package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.alipay.remoting.AsyncContext;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

import java.util.Objects;

public class DefaultActionAfter implements ActionAfter<RequestMessage, ResponseMessage> {
    @Override
    public void execute(final ParamContext paramContext1, final ActionCommand actionCommand, final RequestMessage requestMessage, final ResponseMessage responseMessage) {
        DefaultParamContext paramContext = (DefaultParamContext) paramContext1;
        AsyncContext asyncCtx = paramContext.getAsyncCtx();

        if (Objects.nonNull(asyncCtx)) {
            // 将数据回传给掉用方
            asyncCtx.sendResponse(responseMessage);
        }
    }
}
