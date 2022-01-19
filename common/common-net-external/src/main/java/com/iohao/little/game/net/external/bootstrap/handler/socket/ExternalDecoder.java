package com.iohao.little.game.net.external.bootstrap.handler.socket;

import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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


    public static void main(String[] args) throws Exception {

        String contentStr = "hello, world!";


        ExternalMessage request = new ExternalMessage();
        request.setCmdCode((short) 1);
        // 600 , 700
        request.setCmdMerge(39322300);

        request.setData(contentStr.getBytes());


        ByteBuf buf = Unpooled.buffer(30);
        buf.writeInt(ExternalCont.HEADER_LEN);

        buf.writeShort(request.getCmdCode());
        buf.writeInt(request.getCmdMerge());
        buf.writeByte(request.getProtocolSwitch());
        buf.writeInt(request.getDataLength());
        buf.writeBytes(request.getData());

        String str = ByteBufUtil.hexDump(buf);
        // 000001025802bc000000000d68656c6c6f2c20776f726c6421
        System.out.println(str);
        byte[] a = buf.array();
        System.out.println(Arrays.toString(a));


        Charset utf8 = StandardCharsets.UTF_8;
        System.out.println(buf.toString(utf8));


        ExternalDecoder decoder = new ExternalDecoder();

        decoder.decode(null, buf, new ArrayList<>());
    }
}
