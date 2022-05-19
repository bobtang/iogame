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
package com.iohao.game.bolt.broker.client.external.bootstrap;

import cn.hutool.core.util.IdUtil;
import com.alipay.remoting.rpc.RpcCommandType;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.bolt.broker.client.external.ExternalHelper;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessageCmdCode;
import com.iohao.game.bolt.broker.client.external.session.UserSession;
import com.iohao.game.bolt.broker.client.external.session.UserSessions;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.message.BrokerClientModuleMessage;
import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;

/**
 * @author 渔民小镇
 * @date 2022-01-18
 */
@UtilityClass
public class ExternalKit {
    public RequestMessage convertRequestMessage(ExternalMessage message) {

        BrokerClient brokerClient = (BrokerClient) ExternalHelper.me().getBrokerClient();

        BrokerClientModuleMessage brokerClientModuleMessage = brokerClient.getBrokerClientModuleMessage();

        String id = brokerClientModuleMessage.getId();

        // 元信息
        HeadMetadata headMetadata = new HeadMetadata()
                .setCmdMerge(message.getCmdMerge())
                .setRpcCommandType(RpcCommandType.REQUEST_ONEWAY)
                .setSourceClientId(id);

        // 请求
        RequestMessage requestMessage = new RequestMessage();
        requestMessage
                .setData(message.getData())
                .setHeadMetadata(headMetadata);

        return requestMessage;
    }

    public ExternalMessage convertExternalMessage(ResponseMessage responseMessage) {
        HeadMetadata headMetadata = responseMessage.getHeadMetadata();

        ExternalMessage externalMessage = new ExternalMessage();
        externalMessage.setCmdCode(ExternalMessageCmdCode.biz);
        externalMessage.setCmdMerge(headMetadata.getCmdMerge());
        externalMessage.setResponseStatus((short) responseMessage.getResponseStatus());
        externalMessage.setData(responseMessage.getData());

        return externalMessage;
    }

    public void writeAndFlush(long userId, ExternalMessage message) {

        if (!UserSessions.me().existUserSession(userId)) {
            return;
        }

        UserSession userSession = UserSessions.me().getUserSession(userId);

        Channel channel = userSession.getChannel();

        channel.writeAndFlush(message);
    }

    public long newId() {
        return IdUtil.getSnowflake().nextId();
    }

}
