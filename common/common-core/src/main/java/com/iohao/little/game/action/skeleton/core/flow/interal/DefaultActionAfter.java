package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.alipay.remoting.AsyncContext;
import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

import java.util.Objects;

/**
 * 默认的ActionAfter
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionAfter implements ActionAfter {
    @Override
    public void execute(FlowContext flowContext) {
        final ResponseMessage responseMessage = flowContext.getResponse();

        DefaultParamContext paramContext = (DefaultParamContext) flowContext.getParamContext();

        AsyncContext asyncCtx = paramContext.getAsyncCtx();

        if (Objects.nonNull(asyncCtx)) {
            // 将数据回传给掉用方
            asyncCtx.sendResponse(responseMessage);
        }
    }
}
