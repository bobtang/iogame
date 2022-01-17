package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.ProtoKit;

import java.util.Objects;

/**
 * proto 结果包装器
 *
 * @author 洛朱
 * @date 2022-01-12
 */
public class ProtoActionMethodResultWrap implements ActionMethodResultWrap {
    @Override
    public void wrap(FlowContext flowContext) {
        final ActionCommand actionCommand = flowContext.getActionCommand();
        final ResponseMessage responseMessage = flowContext.getResponse();
        final Object result = flowContext.getMethodResult();

        if (actionCommand.isThrowException() && result instanceof MsgException msgException) {
            // 异常处理
            int code = msgException.getMsgCode();
            responseMessage.setErrorCode(code);
        } else if (Objects.nonNull(result)) {
            // 业务方法返回值
            byte[] dataContent = ProtoKit.toBytes(result);
            responseMessage.setDataContent(dataContent);
            responseMessage.setData(result);
        }

    }
}
