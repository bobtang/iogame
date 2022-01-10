package com.iohao.little.game.net.external.bootstrap;

import com.alipay.remoting.NamedThreadFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;

import java.util.concurrent.ThreadFactory;

/**
 * Server Bootstrap 优化项
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public interface BootstrapOption {
    /**
     * EventLoopGroup bossGroup (连接处理组)
     *
     * @return EventLoopGroup
     */
    EventLoopGroup bossGroup();

    /**
     * EventLoopGroup workerGroup (业务处理组)
     *
     * @return EventLoopGroup
     */
    EventLoopGroup workerGroup();

    /**
     * channelClass
     *
     * @return channelClass
     */
    Class<? extends ServerChannel> channelClass();

    /**
     * 业务 ThreadFactory
     *
     * @return worker ThreadFactory
     */
    static ThreadFactory workerThreadFactory() {
        return new NamedThreadFactory(
                "external-netty-server-worker",
                true);
    }

    /**
     * 连接 ThreadFactory
     *
     * @return boss ThreadFactory
     */
    static ThreadFactory bossThreadFactory() {
        return new NamedThreadFactory(
                "external-netty-server-boss",
                false);
    }
}
