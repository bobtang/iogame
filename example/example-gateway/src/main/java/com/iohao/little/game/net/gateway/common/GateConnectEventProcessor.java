/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.little.game.net.gateway.common;

import com.iohao.example.common.*;
import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.net.BoltServer;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.gateway.module.ModuleInfoManager;
import com.iohao.little.game.net.gateway.module.ModuleInfoProxy;
import com.iohao.little.game.net.kit.ExecutorKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConnectionEventProcessor for ConnectionEventType.CONNECT
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@Slf4j
public class GateConnectEventProcessor implements ConnectionEventProcessor {

    private final AtomicBoolean connected = new AtomicBoolean();
    private final AtomicInteger connectTimes = new AtomicInteger();
    private Connection connection;
    private String remoteAddress;
    private final CountDownLatch latch = new CountDownLatch(1);

    boolean isServer;
    BoltServer server;

    public void setServer(BoltServer server) {
        this.server = server;
        this.isServer = true;
    }

    @Override
    public void onEvent(String remoteAddress, Connection conn) {
        Assert.assertNotNull(remoteAddress);
        doCheckConnection(conn);
        this.remoteAddress = remoteAddress;
        this.connection = conn;
        connected.set(true);
        connectTimes.incrementAndGet();
        latch.countDown();

        // 有客户端连接上来，给客户端发消息
//        extracted();

    }

    private void extracted() {
        executorService.execute(() -> {
            if (!isServer) {
                return;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("server send data to client");

            Apple apple = new Apple();
            apple.setAge(188);

            CmdInfoFlyweightFactory factory = CmdInfoFlyweightFactory.me();
            CmdInfo cmdInfo = factory.getCmdInfo(ActionCont.AppleModule.cmd, ActionCont.AppleModule.name);

            RequestMessage requestMessage = new RequestMessage();
            requestMessage.setCmdInfo(cmdInfo);
            requestMessage.setData(apple);

            ResponseMessage responseMessage = null;
            try {
                ModuleInfoProxy moduleInfo = ModuleInfoManager.me().getModuleInfo(cmdInfo.getCmdMerge());

                for (int i = 0; i < 2; i++) {
                    apple.setAge(i);
                    log.info("--------------------------------- moduleInfo.invokeSync ");
                    responseMessage = (ResponseMessage) moduleInfo.invokeSync(requestMessage);
                    log.info("{}-responseCommand - moduleInfo :{}", i, responseMessage);
                }
            } catch (RemotingException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    ExecutorService executorService = ExecutorKit.newSingleThreadExecutor("server connect");

    /**
     * do check connection
     *
     * @param conn
     */
    private void doCheckConnection(Connection conn) {
        Assert.assertNotNull(conn);
        Assert.assertNotNull(conn.getPoolKeys());
        Assert.assertTrue(conn.getPoolKeys().size() > 0);
        Assert.assertNotNull(conn.getChannel());
        Assert.assertNotNull(conn.getUrl());
        Assert.assertNotNull(conn.getChannel().attr(Connection.CONNECTION).get());
    }

    public boolean isConnected() throws InterruptedException {
        latch.await();
        return this.connected.get();
    }

    public int getConnectTimes() throws InterruptedException {
        latch.await();
        return this.connectTimes.get();
    }

    public Connection getConnection() throws InterruptedException {
        latch.await();
        return this.connection;
    }

    public String getRemoteAddress() throws InterruptedException {
        latch.await();
        return this.remoteAddress;
    }

    public void reset() {
        this.connectTimes.set(0);
        this.connected.set(false);
        this.connection = null;
    }
}
