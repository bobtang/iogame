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
package com.iohao.little.game.net.external.bolt;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.net.external.session.UserSession;
import com.iohao.little.game.net.message.common.ChangeUserIdMessage;
import com.iohao.little.game.net.message.common.ChangeUserIdMessageResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
@Slf4j
public class ExternalChangeUserIdMessageAsyncUserProcessor extends AsyncUserProcessor<ChangeUserIdMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ChangeUserIdMessage request) {

        long userId = request.getUserId();
        long newUserId = request.getNewUserId();

        UserSession userSession = UserSession.me();

        boolean result = userSession.changeUserId(userId, newUserId);

        log.debug("3 对外服变更用户id, 临时userId:{}, 真实userId:{}", userId, newUserId);

        ChangeUserIdMessageResponse response = new ChangeUserIdMessageResponse();
        response.setSuccess(result);
        response.setUserNewId(newUserId);

        asyncCtx.sendResponse(response);
    }

    @Override
    public String interest() {
        return ChangeUserIdMessage.class.getName();
    }
}
