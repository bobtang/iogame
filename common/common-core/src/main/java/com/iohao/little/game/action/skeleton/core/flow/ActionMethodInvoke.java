package com.iohao.little.game.action.skeleton.core.flow;

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
     * @param flowContext flow 上下文
     * @return 返回值
     */
    Object invoke(FlowContext flowContext);

}
