package com.iohao.little.game.action.skeleton.core.flow;

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
     * @param flowContext flow 上下文
     * @return 参数列表 一定不为 null
     */
    Object[] listParam(final FlowContext flowContext);
}
