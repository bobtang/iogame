package com.iohao.little.game.net.external.bootstrap.handler.codec;

import com.iohao.little.game.net.external.bootstrap.handler.ExternalKit;
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
 * @date 2022-01-21
 */
@Slf4j
@ChannelHandler.Sharable
public class ExternalCodecWebsocket extends MessageToMessageCodec<BinaryWebSocketFrame, ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ExternalMessage msg, List<Object> out) throws Exception {
        if (Objects.isNull(msg)) {
            throw new Exception("The encode ExternalMessage is null");
        }

        // 编码器 - 将 ExternalMessage 编码成 字节数组
        int initialCapacity = ExternalKit.messageHeadLen(msg);

        ByteBuf byteBuf = Unpooled.buffer(initialCapacity);

        ExternalEncoder.encode(msg, byteBuf);

        BinaryWebSocketFrame socketFrame = new BinaryWebSocketFrame(byteBuf);

        out.add(socketFrame);

        log.info("写出消息给客户端 : {}", msg);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame binary, List<Object> out) throws Exception {
        // 解码器 - 将字节数组解码成 ExternalMessage
        ByteBuf in = binary.content();
        ExternalMessage message = ExternalDecoder.decode(in);

        out.add(message);
    }
}
