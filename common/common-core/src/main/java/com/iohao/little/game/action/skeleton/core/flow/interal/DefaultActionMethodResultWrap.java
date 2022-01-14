package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * Default ActionMethod 结果包装器
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionMethodResultWrap implements ActionMethodResultWrap<RequestMessage, ResponseMessage> {
    @Override
    public ResponseMessage wrap(final ActionCommand actionCommand, final RequestMessage requestMessage, final Object result) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCmdInfo(actionCommand.getCmdInfo());

        // 业务方法返回值
        responseMessage.setData(result);

        // 异常处理
        if (actionCommand.isHasThrowException() && result instanceof MsgException msgException) {
            int code = msgException.getMsgCode();
            responseMessage.setErrorCode(code);
        }

        return responseMessage;
    }
}
