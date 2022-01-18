package com.iohao.little.game.net.external.bolt;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.handler.ExternalKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 接收来自网关的响应
 * <pre>
 *     把响应 write 到客户端
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-18
 */
@Slf4j
public class ExternalResponseMessageAsyncUserProcessor extends AsyncUserProcessor<ResponseMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ResponseMessage responseMessage) {
        if (log.isDebugEnabled()) {
            log.debug("接收来自网关的响应 {}", responseMessage);
        }

        ExternalMessage message = ExternalKit.convertExternalMessage(responseMessage);

        // 消息编码
        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        ByteBuf byteBuf = Unpooled.buffer(headLen);
        ExternalEncoder.encode(message, byteBuf);

        // 响应结果给用户
        long userId = responseMessage.getUserId();
        Channel channel = UserSession.me().getChannel(userId);

        BinaryWebSocketFrame socketFrame = new BinaryWebSocketFrame(byteBuf);
        channel.writeAndFlush(socketFrame);
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     * 假设 除了需要处理 MyRequest 类型的数据，还要处理 java.lang.String 类型，有两种方式：
     * 1、再提供一个 UserProcessor 实现类，其 interest() 返回 java.lang.String.class.getName()
     * 2、使用 MultiInterestUserProcessor 实现类，可以为一个 UserProcessor 指定 List<String> multiInterest()
     */
    @Override
    public String interest() {
        return ResponseMessage.class.getName();
    }
}
