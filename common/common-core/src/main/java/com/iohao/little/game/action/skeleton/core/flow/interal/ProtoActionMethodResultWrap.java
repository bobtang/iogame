package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.ProtoKit;

import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class ProtoActionMethodResultWrap implements ActionMethodResultWrap<RequestMessage, ResponseMessage> {
    @Override
    public ResponseMessage wrap(final ActionCommand actionCommand, final RequestMessage requestMessage, final Object result) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCmdInfo(actionCommand.getCmdInfo());

        // 异常处理
        if (actionCommand.isHasThrowException() && result instanceof MsgException msgException) {
            int code = msgException.getMsgCode();
            responseMessage.setErrorCode(code);
        } else if (Objects.nonNull(result)) {
            // 业务方法返回值
            byte[] dataContent = ProtoKit.toBytes(result);
            responseMessage.setDataContent(dataContent);
            responseMessage.setData(result);
        }

        return responseMessage;
    }
}
