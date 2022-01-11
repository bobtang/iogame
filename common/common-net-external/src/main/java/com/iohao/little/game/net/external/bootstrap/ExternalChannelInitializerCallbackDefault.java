package com.iohao.little.game.net.external.bootstrap;

import com.iohao.little.game.net.external.bootstrap.handler.ExternalHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 洛朱
 * @date 2022-01-09
 */
@Setter
@Accessors(chain = true)
public class ExternalChannelInitializerCallbackDefault extends ChannelInitializer<SocketChannel> implements ExternalChannelInitializerCallback {

    /** 心跳 */
    ChannelHandler idleHandler;
    /** 心跳时间 */
    int idleTime;
    /** user processors of rpc server */
    ConcurrentHashMap<String, ChannelHandler> channelHandlerProcessors;
    static final int MB = 1024 * 1024;
    // 数据包最大2MB
    static int PACKAGE_MAX_SIZE = 2 * MB;

    @Override
    public void initChannelPipeline(SocketChannel ch) {
        // 编排网关业务
        ChannelPipeline pipeline = ch.pipeline();

        // 数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment。
        pipeline.addLast(new LengthFieldBasedFrameDecoder(PACKAGE_MAX_SIZE,
                // 长度字段的偏移量， 从 0 开始
                0,
                // 长度字段的长度, 使用的是 short ，占用2位；（消息头用的 byteBuf.writeShort 来记录长度的）
                2,
                // 长度调整值： 这里不做任何调整
                0,
                // 跳过的初始字节数： 跳过2位; (跳过消息头的 2 位长度)
                2));

        // 编解码
        pipeline.addLast("decoder", new ExternalDecoder());
        pipeline.addLast("encoder", new ExternalEncoder());

        // 心跳处理
        if (Objects.nonNull(idleHandler)) {
            pipeline.addLast("idleStateHandler",
                    new IdleStateHandler(0, 0, idleTime, TimeUnit.MILLISECONDS));
            pipeline.addLast("idleHandler", idleHandler);
        }

        if (Objects.nonNull(channelHandlerProcessors)) {
            for (Map.Entry<String, ChannelHandler> entry : channelHandlerProcessors.entrySet()) {
                pipeline.addLast(entry.getKey(), entry.getValue());
            }
        }

        pipeline.addLast("handler", new ExternalHandler());
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        this.initChannelPipeline(ch);
    }
}
