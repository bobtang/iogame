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
     * <pre>
     *     类有上有注解 {@link com.iohao.little.game.action.skeleton.annotation.ActionController}
     *     方法有注解 {@link com.iohao.little.game.action.skeleton.annotation.ActionMethod}
     *     只要有这两个注解的，就是业务类
     * </pre>
     *
     * @param paramContext  参数上下方
     * @param actionCommand actionCommand
     * @param controller    actionCommandController
     * @param request       请求参数
     * @param barSkeleton   业务框架
     * @param pars          方法参数列表
     * @return 返回值
     */
    Object invoke(final ParamContext paramContext
            , final ActionCommand actionCommand
            , final Object controller
            , final RequestMessage request
            , final BarSkeleton barSkeleton
            , final Object[] pars);

}
