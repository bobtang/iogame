/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
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
    private final LongAdder tempUserIdAdder = new LongAdder();

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
        // channel 加入到 session 中管理
        UserSession.me().add(userId, channel);
        // false 没有进行身份验证
        channel.attr(UserSessionAttr.verifyIdentity).set(false);

        if (log.isDebugEnabled()) {
            InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String remoteAddress = socketAddress.getAddress().getHostAddress();

            socketAddress = (InetSocketAddress) ctx.channel().localAddress();
            String localAddress = socketAddress != null ? socketAddress.getAddress().getHostAddress() : "null";

            log.debug("localAddress::{}, remoteAddress::{}", localAddress, remoteAddress);
        }
    }

}
