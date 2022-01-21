package com.iohao.game.collect.external.tester.websocket;

import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.net.external.session.UserSessionKit;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
public class ExternalTextHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {

        log.info("accept msg :{}", msg.text());

        msg = new TextWebSocketFrame(" wkao : " + msg.text());
        ctx.writeAndFlush(msg);

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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

}
