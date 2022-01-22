package com.iohao.little.game.net.external.bootstrap.handler.codec;

import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * websocket  编解码
 *
 * @author 洛朱
 * @date 2022-01-22
 */
@Slf4j
@ChannelHandler.Sharable
public class ExternalCodecWebsocketProto extends MessageToMessageCodec<BinaryWebSocketFrame, ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ExternalMessage msg, List<Object> out) throws Exception {
        if (Objects.isNull(msg)) {
            throw new Exception("The encode ExternalMessage is null");
        }

        // 编码器 - ExternalMessage ---> 字节数组
        byte[] bytes = ProtoKit.toBytes(msg);

        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);

        BinaryWebSocketFrame socketFrame = new BinaryWebSocketFrame(byteBuf);

        out.add(socketFrame);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame binary, List<Object> out) {
        // 解码器 - 字节数组 ---> ExternalMessage
        ByteBuf content = binary.content();
        byte[] msgBytes = new byte[content.readableBytes()];
        content.readBytes(msgBytes);

        ExternalMessage message = ProtoKit.parseProtoByte(msgBytes, ExternalMessage.class);

        out.add(message);
    }


}
