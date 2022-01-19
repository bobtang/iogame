package com.iohao.little.game.net.external.bootstrap.handler.websocket;

import com.iohao.little.game.net.external.bootstrap.handler.socket.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 编码器
 *
 * @author 洛朱
 * @date 2022-01-13
 */
@Slf4j
public class ExternalEncoderWebsocket extends MessageToByteEncoder<ExternalMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ExternalMessage message, ByteBuf byteBuf) throws Exception {

        log.info("编码成 字节数组 给到游戏端 {}", message);

        ExternalEncoder.encode(message, byteBuf);

        BinaryWebSocketFrame socketFrame = new BinaryWebSocketFrame(byteBuf);
        channelHandlerContext.channel().writeAndFlush(socketFrame);

    }
}
