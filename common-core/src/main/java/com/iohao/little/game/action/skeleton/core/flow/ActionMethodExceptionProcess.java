package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.BarException;

/**
 * ActionMethod 的异常处理
 */
public interface ActionMethodExceptionProcess {
    BarException processException(Throwable e);
}
