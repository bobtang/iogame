package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * Default ActionMethod 结果包装器
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class ObjectActionMethodResultWrap implements ActionMethodResultWrap {
    @Override
    public void wrap(FlowContext flowContext) {
        final ActionCommand actionCommand = flowContext.getActionCommand();
        final ResponseMessage responseMessage = flowContext.getResponse();
        final Object result = flowContext.getMethodResult();

        // 业务方法返回值
        responseMessage.setData(result);

        // 异常处理
        if (actionCommand.isThrowException() && result instanceof MsgException msgException) {
            int code = msgException.getMsgCode();
            responseMessage.setResponseStatus(code);
        }

    }
}
