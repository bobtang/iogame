package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodResultWrap;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

public class DefaultActionMethodResultWrap implements ActionMethodResultWrap<RequestMessage, ResponseMessage> {
    @Override
    public ResponseMessage wrap(final ActionCommand actionCommand, final RequestMessage requestMessage, final Object result) {

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCmdInfo(actionCommand.getCmdInfo());
        responseMessage.setData(result);

        if (actionCommand.isHasThrowException() && result instanceof BarException barException) {
            int code = barException.getCode();
            responseMessage.setErrorCode(code);
        }

        return responseMessage;
    }
}
