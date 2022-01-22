package com.iohao.little.game.net.external.bootstrap.handler.codec;

import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 * <pre>
 *     将字节数组解码成 ExternalMessage
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-10
 */
@Deprecated
public class ExternalDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> outList) {

        int readableBytesLen = in.readableBytes();
        if (readableBytesLen >= ExternalCont.HEADER_LEN) {
            ExternalMessage request = decode(in);

            outList.add(request);
        }
    }

    public static ExternalMessage decode(ByteBuf in) {
        // 跳过消息头 2字节
        in.skipBytes(2);

        // 2 请求命令类型: 0 心跳，1 业务
        short cmdCode = in.readShort();
        // 1 协议开关，用于一些协议级别的开关控制，比如 安全加密校验等。 : 0 不校验
        byte protocolSwitch = in.readByte();
        // 4 业务路由（高16为主, 低16为子）
        int mergeCmd = in.readInt();
        // 2 响应码。 解码这里用不上，只是为了前端好处理
//            in.skipBytes(2);
        short responseStatus = in.readShort();

        // 4 业务请求体长度
        int contentLen = in.readInt();

        ExternalMessage request = new ExternalMessage();
        request.setCmdCode(cmdCode);
        request.setCmdMerge(mergeCmd);
        request.setProtocolSwitch(protocolSwitch);
        request.setResponseStatus(responseStatus);

        if (contentLen > 0) {
            byte[] content = new byte[contentLen];
            in.readBytes(content, 0, contentLen);
            request.setData(content);
        }
        return request;
    }

}
