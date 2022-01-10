package com.iohao.little.game.net.external.bootstrap;

import com.iohao.little.game.net.external.bootstrap.message.ExternalRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
public class ExternalDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) {

        int readableBytesLen = in.readableBytes();
        if (readableBytesLen >= ExternalCont.REQUEST_HEADER_LEN) {
            // 1
            byte protocolCode = in.readByte();
            // 1
            byte protocolSwitch = in.readByte();
            // 2
            short cmdCode = in.readShort();
            // 4
            int mergeCmd = in.readInt();
            // 4
            int contentLen = in.readInt();

            ExternalRequest request = new ExternalRequest();
            request.setProtocolCode(protocolCode);
            request.setCmdCode(cmdCode);
            request.setMergeCmd(mergeCmd);
            request.setProtocolSwitch(protocolSwitch);

            if (contentLen > 0) {
                byte[] content = new byte[contentLen];
                in.readBytes(content, 0, contentLen);
                request.setContent(content);
            }

            list.add(request);
        }
    }


    public static void main(String[] args) throws Exception {

        String contentStr = "hello, world!";


        ExternalRequest request = new ExternalRequest();
        request.setCmdCode((short) 1);
        // 600 , 700
        request.setMergeCmd(39322300);

        request.setContent(contentStr.getBytes());


        ByteBuf buf = Unpooled.buffer(30);
        buf.writeInt(ExternalCont.REQUEST_HEADER_LEN);

        buf.writeByte(request.getProtocolCode());
        buf.writeShort(request.getCmdCode());
        buf.writeInt(request.getMergeCmd());
        buf.writeByte(request.getProtocolSwitch());
        buf.writeInt(request.getContentLength());
        buf.writeBytes(request.getContent());

        String str = ByteBufUtil.hexDump(buf);
        // 000001025802bc000000000d68656c6c6f2c20776f726c6421
        System.out.println(str);
        byte[] a = buf.array();
        System.out.println(a);


        Charset utf8 = StandardCharsets.UTF_8;
        System.out.println(buf.toString(utf8));


        ExternalDecoder decoder = new ExternalDecoder();

        decoder.decode(null, buf, new ArrayList<>());
    }
}
