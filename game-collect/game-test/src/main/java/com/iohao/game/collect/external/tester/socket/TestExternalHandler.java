package com.iohao.game.collect.external.tester.socket;

import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@Slf4j
@ChannelHandler.Sharable
public class TestExternalHandler extends SimpleChannelInboundHandler<ExternalMessage> {

    LongAdder id = new LongAdder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        log.info("接收用户的数据 {}", message);

        id.increment();

        if (id.longValue() % 5 == 0) {
            message.setResponseStatus(id.shortValue());
        }

        LoginVerify loginVerify = new LoginVerify();
        loginVerify.jwt = ("jwt:" + id.toString());

        message.setData(loginVerify);

        log.info("响应 用户的数据 {}", message);

        // 响应结果给用户
        ctx.writeAndFlush(message);
    }

}
