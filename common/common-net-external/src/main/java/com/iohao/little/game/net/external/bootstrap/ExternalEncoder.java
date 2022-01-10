package com.iohao.little.game.net.external.bootstrap;

import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.bootstrap.message.ExternalResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 *
 * @author 洛朱
 * @date 2022-01-10
 */
public class ExternalEncoder extends MessageToByteEncoder<ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ExternalMessage request, ByteBuf byteBuf) {

        // 消息头长度
        int headLen = ExternalCont.REQUEST_HEADER_LEN + request.getContentLength();
        // 占用2位
        byteBuf.writeShort(headLen);

        // 1
        byteBuf.writeByte(request.getProtocolCode());
        // 1
        byteBuf.writeByte(request.getProtocolSwitch());
        // 2
        byteBuf.writeShort(request.getCmdCode());
        // 4
        byteBuf.writeInt(request.getMergeCmd());
        // 2
        if (request instanceof ExternalResponse response) {
            byteBuf.writeShort(response.getResponseStatus());
        }
        // 4
        byteBuf.writeInt(request.getContentLength());

        if (request.getContentLength() > 0) {
            // 业务请求体
            byteBuf.writeBytes(request.getContent());
        }
    }

}
