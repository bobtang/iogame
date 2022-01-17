package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.annotation.ActionController;

/**
 * ActionController 工厂
 * <pre>
 *     负责创建 用户定义的 Action 业务方法
 * </pre>
 *
 * @param <T> t
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionControllerFactoryBean<T> {
    /**
     * 获取 tcp ActionController 对象
     * <pre>
     * ActionController 对象.
     *     可以是任意java对象
     *     只要添加注解 {@link ActionController}
     *     都属于tcp ActionController 对象
     * </pre>
     *
     * @param actionCommand actionCommand
     * @return ActionController 对象
     */
    T getBean(ActionCommand actionCommand);
}
