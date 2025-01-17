/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.external.core.netty.handler;

import com.iohao.game.external.core.hook.IdleHook;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.message.ExternalMessage;
import com.iohao.game.external.core.message.ExternalMessageCmdCode;
import com.iohao.game.external.core.netty.DefaultExternalCoreSetting;
import com.iohao.game.external.core.netty.session.SocketUserSession;
import com.iohao.game.external.core.session.UserSessions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Objects;

/**
 * 心跳相关的 Handler
 *
 * @author 渔民小镇
 * @date 2023-02-18
 */
@ChannelHandler.Sharable
public final class SocketIdleHandler extends ChannelInboundHandlerAdapter {
    /** 心跳事件回调 */
    final IdleHook<IdleStateEvent> idleHook;
    /** true : 响应心跳给客户端 */
    final boolean pong;
    final UserSessions<ChannelHandlerContext, SocketUserSession> userSessions;

    @SuppressWarnings("unchecked")
    public SocketIdleHandler(DefaultExternalCoreSetting setting) {
        IdleProcessSetting idleProcessSetting = setting.getIdleProcessSetting();

        this.idleHook = idleProcessSetting.getIdleHook();

        this.pong = idleProcessSetting.isPong();

        this.userSessions = (UserSessions<ChannelHandlerContext, SocketUserSession>) setting.getUserSessions();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ExternalMessage externalMessage = (ExternalMessage) msg;

        // 心跳处理
        int cmdCode = externalMessage.getCmdCode();

        if (cmdCode == ExternalMessageCmdCode.idle) {

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

            var userSession = userSessions.getUserSession(ctx);

            // 执行用户定义的心跳回调事件处理
            if (Objects.nonNull(idleHook)) {
                close = idleHook.callback(userSession, event);
            }

            // close ctx
            if (close) {
                this.userSessions.removeUserSession(userSession);
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
