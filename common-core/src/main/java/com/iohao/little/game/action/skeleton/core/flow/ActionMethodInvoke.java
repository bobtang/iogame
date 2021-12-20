package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * ActionMethod Invoke
 * <pre>
 *     掉用业务层的方法 (即对外提供的方法)
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodInvoke {
    /**
     * 具体的业务方法掉用
     *
     * @param paramContext  参数上下方
     * @param actionCommand actionCommand
     * @param controller    actionCommandController
     * @param request       请求参数
     * @param barSkeleton   业务框架
     * @return 返回值
     */
    Object invoke(final ParamContext paramContext
            , final ActionCommand actionCommand
            , final Object controller
            , final RequestMessage request
            , final BarSkeleton barSkeleton);

}
