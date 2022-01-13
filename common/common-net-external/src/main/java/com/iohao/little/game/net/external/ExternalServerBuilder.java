package com.iohao.little.game.net.external;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.iohao.little.game.net.external.bootstrap.BootstrapOption;
import com.iohao.little.game.net.external.bootstrap.ExternalChannelInitializerCallbackDefault;
import com.iohao.little.game.net.external.bootstrap.handler.ExternalHandler;
import com.iohao.little.game.net.external.bootstrap.option.BootstrapOptionForLinux;
import com.iohao.little.game.net.external.bootstrap.option.BootstrapOptionForMac;
import com.iohao.little.game.net.external.bootstrap.option.BootstrapOptionForWindows;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对外服务器 - 构建器
 * <p>
 * 如果不配置默认如下
 * <ul>
 *     <li>serverBootstrapOption : 根据当前操作系统自动创建</li>
 * </ul>
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public class ExternalServerBuilder {

    /** 服务器 */
    final ServerBootstrap bootstrap = new ServerBootstrap();
    /** user processors of rpc server */
    final ConcurrentHashMap<String, ChannelHandler> channelHandlerProcessors = new ConcurrentHashMap<>(4);

    String ip;
    int port;

    /** Bootstrap 优化项 */
    @Setter
    BootstrapOption bootstrapOption;

    /** 心跳时间 */
    int idleTime = 10;

    ExternalServerBuilder(int port) {
        this.port = port;
        this.ip = new InetSocketAddress(port).getAddress().getHostAddress();
    }

    public ExternalServerBuilder registerChannelHandler(String name, ChannelHandler processor) {
        channelHandlerProcessors.put(name, processor);
        return this;
    }

    public ExternalServerBuilder enableIdle(int idleTime) {
        this.idleTime = idleTime;
        return this.enableIdle();
    }

    public ExternalServerBuilder enableIdle() {
//        serverIdleHandler = new ServerIdleHandler();
        return this;
    }

    private void defaultSetting() {
        if (Objects.isNull(bootstrapOption)) {
            bootstrapOption = createServerBootstrapOption();
        }

        // 如果没有 handler 默认给一个
        if (channelHandlerProcessors.isEmpty()) {
            registerChannelHandler("externalHandler", new ExternalHandler());
        }
    }

    private void check() throws RuntimeException {
        if (this.port == 0) {
            throw new RuntimeException("port err");
        }
    }

    public ExternalServer build() {
        this.defaultSetting();

        this.check();

        ExternalChannelInitializerCallbackDefault callbackDefault = new ExternalChannelInitializerCallbackDefault()
                .setIdleTime(this.idleTime)
                .setChannelHandlerProcessors(this.channelHandlerProcessors);

        bootstrap
                // netty 核心组件. (1 连接创建线程组, 2 业务处理线程组)
                .group(bootstrapOption.bossGroup(), bootstrapOption.workerGroup())
                .channel(bootstrapOption.channelClass())

                .childHandler(callbackDefault)
        ;

        new SocketServerBootstrapSetting().setting(bootstrap);

        return new ExternalServer(this);
    }

    private BootstrapOption createServerBootstrapOption() {
        OsInfo osInfo = SystemUtil.getOsInfo();

        // 根据系统内核来优化
        if (osInfo.isLinux()) {
            // linux
            return new BootstrapOptionForLinux();
        } else if (osInfo.isMac() || osInfo.isMacOsX()) {
            // mac
            return new BootstrapOptionForMac();
        }

        // other system
        return new BootstrapOptionForWindows();
    }

    interface ServerBootstrapSetting {
        void setting(ServerBootstrap serverBootstrap);
    }

    static class SocketServerBootstrapSetting implements ServerBootstrapSetting {

        @Override
        public void setting(ServerBootstrap serverBootstrap) {

            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

            /*
             * 是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）
             * 并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活
             */
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            /*
             * BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
             * 用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，
             * 使用默认值100
             */
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 100);

            /*
             * 在TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，
             * 同时，对方接收到数据，也需要发送ACK表示确认。
             * 为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。
             * 这里就涉及到一个名为Nagle的算法，该算法的目的就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。
             */
            serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
        }
    }

    static class WebSocketServerBootstrapSetting implements ServerBootstrapSetting {

        @Override
        public void setting(ServerBootstrap serverBootstrap) {
            serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        }
    }

}
