package com.iohao.little.game.net.external.bootstrap.handler.websocket;

import com.iohao.little.game.net.external.bootstrap.handler.socket.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketExternalDecoder extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame binary) throws Exception {


        ByteBuf in = binary.content();
        // 跳过消息头 2字节
        in.skipBytes(2);

        // 消息解码
        ExternalMessage message = ExternalDecoder.decode(in);

        log.info("WebSocketExternalDecoder {}", message);
        //
        ctx.fireChannelRead(message);
    }


    public static WebSocketExternalDecoder me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final WebSocketExternalDecoder ME = new WebSocketExternalDecoder();
    }
}
