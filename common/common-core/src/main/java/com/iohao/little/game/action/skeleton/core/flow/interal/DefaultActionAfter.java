package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.rpc.RpcCommandType;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
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
        final ResponseMessage response = flowContext.getResponse();

        AsyncContext asyncCtx = getAsyncContext(flowContext);

        if (Objects.isNull(asyncCtx)) {
            return;
        }

        // 有错误就响应给掉用方
        if (response.hasError()) {
            asyncCtx.sendResponse(response);
            return;
        }

        // action 方法返回值是 void 的，不做处理
        ActionCommand actionCommand = flowContext.getActionCommand();
        if (actionCommand.getActionMethodReturnInfo().isVoid()) {
            return;
        }

        // 将数据回传给掉用方
        asyncCtx.sendResponse(response);
    }

    private AsyncContext getAsyncContext(FlowContext flowContext) {
        ResponseMessage response = flowContext.getResponse();
        byte rpcCommandType = response.getRpcCommandType();

        DefaultParamContext paramContext = flowContext.getParamContext();

        if (rpcCommandType != RpcCommandType.REQUEST_ONEWAY) {
            return paramContext.getAsyncCtx();
        } else {
            return paramContext.getServerContext();
        }
    }
}
