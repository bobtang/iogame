package com.iohao.little.game.net.external.bootstrap.codec;

import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * 编码器
 *
 * @author 洛朱
 * @date 2022-01-13
 */
public class ExternalEncoderWebsocket extends MessageToMessageEncoder<ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ExternalMessage message, List<Object> out) throws Exception {
        // 消息总长度 = 消息头2 + 协议体13
        // 2 + (2 + 1 + 4 + 2 + 4) = 15
        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();

        ByteBuf byteBuf = Unpooled.buffer(headLen);

        ExternalEncoder.encode(message, byteBuf);

        // 然后下面再转成websocket二进制流
        WebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
        out.add(frame);
    }
}
