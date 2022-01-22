package com.iohao.little.game.net.external.bootstrap.handler;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.net.external.bootstrap.ExternalKit;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.net.external.session.UserSessionKit;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 对外服 业务处理类
 * <pre>
 *     负责把游戏端的请求 转发给网关
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@ChannelHandler.Sharable
public class ExternalBizHandler extends SimpleChannelInboundHandler<ExternalMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        log.info("x 接收客户端消息 {}", message);

//        ctx.writeAndFlush(message);
//
//        if (true) {
//            return;
//        }

        // 将 message 转换成 RequestMessage
        RequestMessage requestMessage = ExternalKit.convertRequestMessage(message);

        // 转发到网关
        long userId = UserSession.me().getUserId(ctx.channel());
        requestMessage.setUserId(userId);

        String address = ExternalServerKit.address();
        RpcClient rpcClient = ExternalServerKit.rpcClient;

        try {
            log.info("external 转发到网关");
            rpcClient.oneway(address, requestMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("玩家下线");
        UserSession.me().remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("创建 websocket 链接玩家 {}", 1);

        UserSessionKit.channelActive(ctx);

        log.info("当前玩家数量： {}", UserSession.me().countOnline());

    }
}
