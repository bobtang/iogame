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
package com.iohao.little.game.net.client.kit;

import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ChangeUserIdMessage;
import com.iohao.little.game.net.message.common.ChangeUserIdMessageResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 变更用户 id
 * <pre>
 *     用户连接登录编写 文档
 *     https://www.yuque.com/iohao/game/tywkqv
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@UtilityClass
public class ChangeUserIdKit {
    /**
     * 变更用户的 userId
     * <pre>
     *     玩家真正的登录
     * </pre>
     *
     * @param flowContext 业务框架 flow上下文
     * @param newUserId   一般从数据库中获取
     * @return true 变更成功
     */
    public boolean changeUserId(FlowContext flowContext, long newUserId) {
        // 这个 userId 一般是首次建立连接时，系统随机分配的临时 id
        long userId = flowContext.getUserId();

        ChangeUserIdMessage userIdMessage = new ChangeUserIdMessage();
        userIdMessage.setUserId(userId);
        userIdMessage.setNewUserId(newUserId);

        log.debug("1 逻辑服 {}", userIdMessage);

        try {
            DefaultParamContext paramContext = flowContext.getParamContext();
            BoltClientProxy boltClientProxy = (BoltClientProxy) paramContext.getServerContext();
            ChangeUserIdMessageResponse changeUserIdMessageResponse = (ChangeUserIdMessageResponse) boltClientProxy
                    .invokeSync(userIdMessage);

            log.debug("5 逻辑服 {}", changeUserIdMessageResponse);

            if (Objects.isNull(changeUserIdMessageResponse) || !changeUserIdMessageResponse.isSuccess()) {
                return false;
            }
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

        ResponseMessage response = flowContext.getResponse();
        response.setUserId(newUserId);

        return true;
    }
}
