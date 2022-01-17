package com.iohao.little.game.net.external.bolt;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 接收并处理 来自网关的广播消息
 *
 * @author 洛朱
 * @date 2022-01-16
 */
@Slf4j
public class ExternalBroadcastMessageAsyncUserProcess extends AsyncUserProcessor<BroadcastMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, BroadcastMessage message) {

        ExternalMessage externalMessage = convert(message.getResponseMessage());

        // 推送消息到真实用户
        if (message.isBroadcastAll()) {
            // 给全体推送
            UserSession.me().broadcast(externalMessage);
            return;
        }

        // 给用户列表推送
        if (Objects.nonNull(message.getUserIdList())) {

            for (Long userId : message.getUserIdList()) {
                writeAndFlush(userId, externalMessage);
            }

            return;
        }

        // 推送给单个用户
        long userId = message.getResponseMessage().getUserId();
        writeAndFlush(userId, externalMessage);
    }

    private void writeAndFlush(long userId, ExternalMessage message) {
        UserSession session = UserSession.me();
        Channel channel = session.getChannel(userId);

        if (Objects.isNull(channel)) {
            // TODO: 2022/1/16 用户不在线, 可以做点其他事

            return;
        }

        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        ByteBuf byteBuf = Unpooled.buffer(headLen);
        ExternalEncoder.encode(message, byteBuf);

        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(byteBuf);

        channel.writeAndFlush(binaryWebSocketFrame);
    }

    private ExternalMessage convert(ResponseMessage responseMessage) {

        ExternalMessage externalMessage = new ExternalMessage();
        externalMessage.setCmdCode((short) 1);
        externalMessage.setCmdMerge(responseMessage.getCmdMerge());
        externalMessage.setResponseStatus((short) responseMessage.getErrorCode());
        externalMessage.setData(responseMessage.getDataContent());

        return externalMessage;
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     * 假设 除了需要处理 MyRequest 类型的数据，还要处理 java.lang.String 类型，有两种方式：
     * 1、再提供一个 UserProcessor 实现类，其 interest() 返回 java.lang.String.class.getName()
     * 2、使用 MultiInterestUserProcessor 实现类，可以为一个 UserProcessor 指定 List<String> multiInterest()
     */
    @Override
    public String interest() {
        return BroadcastMessage.class.getName();
    }
}
