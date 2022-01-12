package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.iohao.little.game.net.common.BoltServer;
import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GateConnectionEventProcessor implements ConnectionEventProcessor {

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
    }


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
