package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.core.flow.FlowAttr;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.ProtoKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * proto 结果包装器
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@Slf4j
public class DefaultActionMethodResultWrap implements ActionMethodResultWrap {
    @Override
    public void wrap(FlowContext flowContext) {
        final ResponseMessage responseMessage = flowContext.getResponse();
        // 业务方法的返回值
        final Object result = flowContext.getMethodResult();

        if (flowContext.isError()) {
            MsgException msgException = (MsgException) result;
            // 异常处理
            int code = msgException.getMsgCode();
            responseMessage.setResponseStatus(code);
            flowContext.option(FlowAttr.msgException, msgException.getMessage());
            return;
        }

        if (Objects.isNull(result) && !flowContext.getActionCommand().getActionMethodReturnInfo().isVoid()) {
            return;
        }

        // 业务方法返回值
        byte[] dataContent = ProtoKit.toBytes(result);
        responseMessage.setDataContent(dataContent);
    }
}
