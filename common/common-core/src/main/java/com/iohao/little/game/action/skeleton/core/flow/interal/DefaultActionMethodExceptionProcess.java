package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodExceptionProcess;

/**
 * default ActionMethodExceptionProcess
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionMethodExceptionProcess implements ActionMethodExceptionProcess {
    @Override
    public MsgException processException(Throwable e) {

        if (e instanceof MsgException msgException) {
            return msgException;
        }

        return new MsgException(ActionErrorEnum.systemOtherErrCode);
    }
}
