package com.iohao.little.game.net.external.bootstrap.option;

import com.iohao.little.game.net.external.bootstrap.BootstrapOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;

/**
 * 服务端 for Mac nio 处理类
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public class BootstrapOptionForMac implements BootstrapOption {
    @Override
    public EventLoopGroup bossGroup() {
        return new KQueueEventLoopGroup(
                1,
                BootstrapOption.bossThreadFactory()
        );
    }

    @Override
    public EventLoopGroup workerGroup() {
        int availableProcessors = Runtime.getRuntime().availableProcessors() * 2;

        return new KQueueEventLoopGroup(
                availableProcessors,
                BootstrapOption.workerThreadFactory()
        );
    }

    @Override
    public Class<? extends ServerChannel> channelClass() {
        return KQueueServerSocketChannel.class;
    }
}
