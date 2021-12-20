package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.BarException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodExceptionProcess;

/**
 * default ActionMethodExceptionProcess
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionMethodExceptionProcess implements ActionMethodExceptionProcess {
    @Override
    public BarException processException(Throwable e) {

        if (e instanceof BarException barException) {
            return barException;
        }

        return new BarException(e.getMessage(), -1000);
    }
}
