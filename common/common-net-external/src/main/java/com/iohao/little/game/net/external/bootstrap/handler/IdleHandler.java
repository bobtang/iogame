/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.little.game.net.external.bootstrap.handler;

import com.iohao.little.game.net.external.bootstrap.heart.IdleCallback;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 心跳 handler
 *
 * @author 洛朱
 * @date 2022-03-13
 */
@Slf4j
@ChannelHandler.Sharable
public class IdleHandler extends ChannelInboundHandlerAdapter {

    /** 心跳事件回调 */
    final IdleCallback idleCallback;
    /** true : 响应心跳给客户端 */
    final boolean pong;

    public IdleHandler(IdleCallback idleCallback, boolean pong) {
        this.idleCallback = idleCallback;
        this.pong = pong;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ExternalMessage externalMessage = (ExternalMessage) msg;

        // 心跳处理
        int cmdCode = externalMessage.getCmdCode();
        if (cmdCode == 0) {

            if (this.pong) {
                ctx.writeAndFlush(externalMessage);
            }

            return;
        }

        // 不是心跳请求. 交给下一个业务处理 (handler) , 下一个业务指的是你编排 handler 时的顺序
        ctx.fireChannelRead(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {

            boolean close = true;

            long userId = UserSession.me().getUserId(ctx.channel());

            // 执行用户定义的心跳回调事件处理
            if (Objects.nonNull(idleCallback)) {
                close = idleCallback.callback(ctx, event, userId);
            }

            // close ctx
            if (close) {
                UserSession.me().remove(ctx.channel());
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
