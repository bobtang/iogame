package com.iohao.little.game.net.external.bootstrap;

import io.netty.channel.socket.SocketChannel;

/**
 * Channel 初始化的业务编排 (自定义业务编排)
 *
 * @author 洛朱
 * @date 2022-01-09
 */
public interface ExternalChannelInitializerCallback  {
    /**
     * 编排业务
     *
     * @param ch ch
     */
    void initChannelPipeline(SocketChannel ch);
}
