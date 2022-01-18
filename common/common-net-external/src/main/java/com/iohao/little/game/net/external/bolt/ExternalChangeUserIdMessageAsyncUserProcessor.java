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

        log.info("3 对外服变更用户id, 临时userId:{}, 真实userId:{}", userId, newUserId);

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
