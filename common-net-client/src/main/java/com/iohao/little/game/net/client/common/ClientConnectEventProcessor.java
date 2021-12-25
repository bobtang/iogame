package com.iohao.little.game.net.client.common;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.iohao.little.game.kit.ExecutorKit;
import com.iohao.little.game.net.message.common.ModuleKey;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ClientConnectEventProcessor implements ConnectionEventProcessor {

    private final AtomicBoolean connected = new AtomicBoolean();
    private final AtomicInteger connectTimes = new AtomicInteger();
    private Connection connection;
    private String remoteAddress;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Setter
    ModuleKey moduleKey;

    @Override
    public void onEvent(String remoteAddress, Connection conn) {
        Assert.assertNotNull(remoteAddress);
        doCheckConnection(conn);
        this.remoteAddress = remoteAddress;
        this.connection = conn;
        connected.set(true);
        connectTimes.incrementAndGet();
        latch.countDown();

        // 设置连接
        var boltClientInfo = BoltClientProxyManager.me().getBoltClientProxy(moduleKey);
        boltClientInfo.setConnection(conn);
        log.info("~~~~~~~~~~~ client connect: {}", latch);
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
