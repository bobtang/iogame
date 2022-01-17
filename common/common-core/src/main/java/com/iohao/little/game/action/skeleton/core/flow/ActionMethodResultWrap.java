package com.iohao.little.game.action.skeleton.core.flow;

/**
 * ActionMethod 结果包装器
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodResultWrap {
    /**
     * 包装结果
     * <pre>
     *     把用户的业务方法返回值包装成 Response
     * </pre>
     *
     * @param flowContext flow 上下文
     */
    void wrap(FlowContext flowContext);
}
