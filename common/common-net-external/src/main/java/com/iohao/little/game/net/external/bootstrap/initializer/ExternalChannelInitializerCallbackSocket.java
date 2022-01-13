package com.iohao.little.game.net.external.bootstrap.initializer;

import com.iohao.little.game.net.external.bootstrap.ExternalChannelInitializerCallback;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ChannelPipeline 初始化 for tcp
 * <pre>
 *     给初始化的 channel 编排 handler
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-09
 */
@Setter
@Accessors(chain = true)
public class ExternalChannelInitializerCallbackSocket extends ChannelInitializer<SocketChannel> implements ExternalChannelInitializerCallback {

    ExternalChannelInitializerCallbackOption option;

    @Override
    public void initChannelPipeline(SocketChannel ch) {

        // 编排网关业务
        ChannelPipeline pipeline = ch.pipeline();

        // 数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment。
        pipeline.addLast(new LengthFieldBasedFrameDecoder(option.packageMaxSize,
                // 长度字段的偏移量， 从 0 开始
                0,
                // 长度字段的长度, 使用的是 short ，占用2位；（消息头用的 byteBuf.writeShort 来记录长度的）
                2,
                // 长度调整值： 这里不做任何调整
                -2,
                // 跳过的初始字节数： 跳过2位; (跳过消息头的 2 位长度)
                2));

        // 编解码
        option.codec(pipeline);

        // 心跳
        option.idleHandler(pipeline);

        // 业务 handler
        option.channelHandlerProcessors(pipeline);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        this.initChannelPipeline(ch);
    }
}
