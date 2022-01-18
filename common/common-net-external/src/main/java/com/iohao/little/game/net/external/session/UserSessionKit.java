package com.iohao.little.game.net.external.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
@Slf4j
@UtilityClass
public class UserSessionKit {
    /** 临时 userId 生成 */
    final LongAdder tempUserIdAdder = new LongAdder();


    public void channelActive(ChannelHandlerContext ctx) {
        if (tempUserIdAdder.longValue() == 0) {
            tempUserIdAdder.add(Integer.MAX_VALUE);
            tempUserIdAdder.increment();
        }

        // 首次连接上来，是没有 id 的, 生成一个临时 userId
        // TODO: 2022/1/18 后面改为雪花生成 , 否则部署多台对外服时，会有id冲突问题
        tempUserIdAdder.increment();
        long userId = tempUserIdAdder.longValue();

        Channel channel = ctx.channel();
        UserSession.me().add(userId, channel);
        channel.attr(UserSessionAttr.verifyIdentity).set(false);

        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String remoteAddress = socketAddress.getAddress().getHostAddress();

        socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        String localAddress = socketAddress != null ? socketAddress.getAddress().getHostAddress() : "null";

        log.trace("localAddress::{}, remoteAddress::{}", localAddress, remoteAddress);
    }

}
