package com.iohao.little.game.net.external.bootstrap.codec;

import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
public class ExternalDecoderWebsocket extends MessageToMessageDecoder<BinaryWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame frame, List<Object> outList) throws Exception {
        ByteBuf in = frame.content();

        // 消息头
        short headLen = in.readShort();

        ExternalMessage request = ExternalDecoder.decode(in);

        outList.add(request);

    }
}
