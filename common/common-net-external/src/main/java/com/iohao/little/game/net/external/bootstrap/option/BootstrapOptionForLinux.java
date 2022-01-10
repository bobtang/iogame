package com.iohao.little.game.net.external.bootstrap.option;

import com.iohao.little.game.net.external.bootstrap.BootstrapOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

/**
 * 服务端 for linux nio 处理类
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public class BootstrapOptionForLinux implements BootstrapOption {
    @Override
    public EventLoopGroup bossGroup() {
        return new EpollEventLoopGroup(
                1,
                BootstrapOption.bossThreadFactory()
        );
    }

    @Override
    public EventLoopGroup workerGroup() {

        int availableProcessors = Runtime.getRuntime().availableProcessors() * 2;

        return new EpollEventLoopGroup(
                availableProcessors,
                BootstrapOption.workerThreadFactory()
        );
    }

    @Override
    public Class<? extends ServerChannel> channelClass() {
        return EpollServerSocketChannel.class;
    }
}
