package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.BarException;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodExceptionProcess;

public class DefaultActionMethodExceptionProcess implements ActionMethodExceptionProcess {
    @Override
    public BarException processException(Throwable e) {

        if (e instanceof BarException barException) {
            return barException;
        }

        return new BarException(e.getMessage(), -1000);
    }
}
