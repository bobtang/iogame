package com.iohao.little.game.net.external.bootstrap.handler;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 接收真实用户的请求，把请求数据转发到对应的业务逻辑服
 *
 * @author 洛朱
 * @date 2022-01-10
 */
@Slf4j
public class ExternalHandler extends SimpleChannelInboundHandler<ExternalMessage> {
    UserSessionManager sessionManager = UserSessionManager.me();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        log.info("{}", message);

        // 将 message 转换成 RequestMessage
        RequestMessage requestMessage = convertRequestMessage(message);

        // 设置当前操作用户
        long userId = sessionManager.getUserId(ctx.channel());
        requestMessage.setUserId(userId);

        // 执行请求,得到响应 （）
        ExternalMessage response = request(message, requestMessage);

        // 响应结果给用户
        ctx.writeAndFlush(response);
    }

    private RequestMessage convertRequestMessage(ExternalMessage message) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdMerge(message.getCmdMerge());
        requestMessage.setDataContent(message.getData());
        return requestMessage;
    }

    private ExternalMessage request(ExternalMessage message, RequestMessage requestMessage) {

        // 响应
        ResponseMessage responseMessage;

        try {
            // TODO: 2022/1/11 转发到网关
            RpcClient rpcClient = ExternalServerKit.rpcClient;
            String address = ExternalServerKit.address();
            responseMessage = (ResponseMessage) rpcClient.invokeSync(address, requestMessage, 1000);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
            // 超时错误
            message.setResponseStatus((short) -1);
            return message;
        }

        if (Objects.nonNull(responseMessage)) {
            Object data = responseMessage.getData();
            byte[] bytes = ProtoKit.toBytes(data);
            message.setData(bytes);

            message.setResponseStatus((short) responseMessage.getErrorCode());

        }

        return message;
    }
}
