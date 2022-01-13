package com.iohao.little.game.net.external.bootstrap.codec;

import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * <pre>
 *     将 ExternalMessage 编码成 字节数组
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-10
 */
public class ExternalEncoder extends MessageToByteEncoder<ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ExternalMessage message, ByteBuf byteBuf) {

        encode(message, byteBuf);
    }

    public static void encode(ExternalMessage message, ByteBuf byteBuf) {

        // 消息总长度 = 消息头2 + 协议体13
        // 2 + (2 + 1 + 4 + 2 + 4) = 15
        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();

        // 2 消息头 长度
        byteBuf.writeShort(headLen);

        // 2 请求命令类型: 0 心跳，1 业务
        byteBuf.writeShort(message.getCmdCode());
        // 1 协议开关，用于一些协议级别的开关控制，比如 安全加密校验等。 : 0 不校验
        byteBuf.writeByte(message.getProtocolSwitch());
        // 4 业务路由（高16为主, 低16为子）
        byteBuf.writeInt(message.getCmdMerge());

        // 2 响应码。从字段精简的角度，我们不可能每次响应都带上完整的异常栈给客户端排查问题，
        // 因此，我们会定义一些响应码，通过编号进行网络传输，方便客户端定位问题。
        byteBuf.writeShort(message.getResponseStatus());

        // 4 业务请求体长度
        byteBuf.writeInt(message.getDataLength());

        if (message.getDataLength() > 0) {
            // 业务请求体
            byteBuf.writeBytes(message.getData());
        }
    }

}
