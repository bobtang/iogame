package com.iohao.little.game.net.external.bootstrap.option;

import com.iohao.little.game.net.external.bootstrap.BootstrapOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端 for other nio 处理类
 *
 * <pre>
 * other system:
 *     Windows, Solaris
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public class BootstrapOptionForOther implements BootstrapOption {
    @Override
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup(
                1,
                BootstrapOption.bossThreadFactory()
        );
    }

    @Override
    public EventLoopGroup workerGroup() {
        int availableProcessors = Runtime.getRuntime().availableProcessors() * 2;

        return new NioEventLoopGroup(
                availableProcessors,
                BootstrapOption.workerThreadFactory()
        );
    }

    @Override
    public Class<? extends ServerChannel> channelClass() {
        return NioServerSocketChannel.class;
    }
}
