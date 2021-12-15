package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * ActionMethod Invoke
 * <p>
 * 掉用业务层的方法 (即对外提供的方法)
 * </p>
 */
public interface ActionMethodInvoke {
    Object invoke(final ParamContext paramContext
            , final ActionCommand actionCommand
            , final Object controller
            , final RequestMessage request
            , final BarSkeleton barSkeleton);

}
