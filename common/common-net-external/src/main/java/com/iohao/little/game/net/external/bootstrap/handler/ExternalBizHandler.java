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

        // 将 message 转换成 RequestMessage
        RequestMessage requestMessage = ExternalKit.convertRequestMessage(message);

        // 设置请求用户的id
        long userId = UserSession.me().getUserId(ctx.channel());
        requestMessage.setUserId(userId);

        try {
//            log.info("external 转发到网关");
            String address = ExternalServerKit.address();
            RpcClient rpcClient = ExternalServerKit.rpcClient;
            // 由内部逻辑服转发用户请求到网关服，在由网关服转到具体的业务逻辑服
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
