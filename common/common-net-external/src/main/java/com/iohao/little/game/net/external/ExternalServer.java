package com.iohao.little.game.net.external;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 对外的服务器
 *
 * @author 洛朱
 * @date 2022-01-09
 */
@Slf4j
public class ExternalServer {
    ExternalServerBuilder builder;
    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    ExternalServer(ExternalServerBuilder builder) {
        this.builder = builder;
    }

    protected boolean doStart() throws InterruptedException {

        ServerBootstrap bootstrap = builder.bootstrap;

        int port = builder.port;
        String ip = builder.ip;

        // channelFuture
        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(ip, port)).sync();

        if (port == 0 && channelFuture.isSuccess()) {
            InetSocketAddress localAddress = (InetSocketAddress) channelFuture.channel()
                    .localAddress();
            builder.port = localAddress.getPort();
            log.info("rpc server start with random port: {}!", builder.port);
        }

        return channelFuture.isSuccess();
    }

    public void startup() {
//        this.isStarted.compareAndSet(false, true);

        try {
            doStart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {

    }

    public static ExternalServerBuilder newBuilder(int port) {
        return new ExternalServerBuilder(port);
    }

    public static void main(String[] args) {
        int port = 22022;

        ExternalServerBuilder builder = ExternalServer.newBuilder(port);

        ExternalServer externalServer = builder.build();

        externalServer.startup();
        System.out.println("OK!");

    }
}
