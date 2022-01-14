package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * action 方法参数解析器 actionCommand
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodParamParser {
    /** 方法空参数 */
    Object[] METHOD_PARAMS = new Object[0];

    /**
     * 参数解析
     *
     * @param paramContext  参数上下文
     * @param actionCommand actionCommand
     * @param request       请求
     * @return 参数列表 一定不为 null
     */
    Object[] listParam(final ParamContext paramContext, final ActionCommand actionCommand, final RequestMessage request);
}
