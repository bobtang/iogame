package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * ActionMethod 的异常处理
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodExceptionProcess {
    /**
     * 异常处理
     *
     * @param e e
     * @return BarException
     */
    MsgException processException(Throwable e);
}
