package com.iohao.little.game.net.external.bootstrap.handler.websocket.old;

import com.iohao.little.game.net.external.bootstrap.handler.socket.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketPacketHandle extends SimpleChannelInboundHandler<WebSocketFrame> {

    /**
     * <pre>
     *     接收客户端发送的消息 channel 通道 Read
     *     从通道中读取数据，也就是服务端接收客户端发来的数据。
     *     但是这个数据在不进行解码时它是ByteBuf类型的
     * </pre>
     *
     * @param ctx   ctx
     * @param frame msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 1.判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            ctx.close();
            return;
        }

        // 2.判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content()));
            return;
        }

        // 3.如果是文本消息
        if (frame instanceof TextWebSocketFrame) {
            log.info("主动断开连接, 因为错误的通讯格式 TextWebSocketFrame ， ip:port [{}]", ctx.channel().remoteAddress());
            // TODO: 2022/1/19 close ctx
            ctx.close();
            return;
        }

        // 4.如果是字节消息
        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binary = (BinaryWebSocketFrame) frame;
            ByteBuf in = binary.content();
            // 跳过消息头 2字节
            in.skipBytes(2);
            ExternalMessage message = ExternalDecoder.decode(in);

            ctx.fireChannelRead(message);
//            byte[] contentBytes = new byte[frame.content().readableBytes()];
//            frame.content().readBytes(contentBytes);
//            ctx.fireChannelRead(contentBytes);
        }
    }


    public static WebSocketPacketHandle me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final WebSocketPacketHandle ME = new WebSocketPacketHandle();
    }
}
