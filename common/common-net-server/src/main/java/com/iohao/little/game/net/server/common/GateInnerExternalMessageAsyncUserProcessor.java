package com.iohao.little.game.net.server.common;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.net.message.common.InnerExternalMessage;
import com.iohao.little.game.net.server.GateKit;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public class GateInnerExternalMessageAsyncUserProcessor extends AsyncUserProcessor<InnerExternalMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, InnerExternalMessage message) {
        RequestMessage requestMessage = message.getRequestMessage();

        GateKit.sendToLogicServer(asyncCtx, requestMessage);
    }

    @Override
    public String interest() {
        return InnerExternalMessage.class.getName();
    }
}
