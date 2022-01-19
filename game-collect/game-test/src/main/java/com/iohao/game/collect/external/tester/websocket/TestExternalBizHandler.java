package com.iohao.game.collect.external.tester.websocket;

import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.net.external.session.UserSessionAttr;
import com.iohao.little.game.net.external.session.UserSessionKit;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@ChannelHandler.Sharable
public class TestExternalBizHandler extends SimpleChannelInboundHandler<ExternalMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        log.info("x 接收客户端消息 {}", message);

        ctx.writeAndFlush(message);

//        LoginVerify loginVerify = new LoginVerify();
//        loginVerify.jwt = ("jwt:" + id.toString());
//        message.setData(loginVerify);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("玩家下线 {}", ctx.channel().attr(UserSessionAttr.userId));
        UserSession.me().remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        UserSessionKit.channelActive(ctx);

        log.info("当前玩家数量： {}", UserSession.me().countOnline());

    }
}
