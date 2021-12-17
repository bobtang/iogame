package com.iohao.little.game.action.skeleton.core;

/**
 * single工厂对象 <BR>
 *
 * @author 洛朱
 * @date 2021/12/12
 */
public final class DefaultActionControllerFactoryBean<T> implements ActionControllerFactoryBean<T> {
    @Override
    @SuppressWarnings("unchecked")
    public T getBean(ActionCommand actionCommand) {

        if (actionCommand.isCreateSingleActionCommandController()) {
            return (T) actionCommand.getActionController();
        }

        return (T) actionCommand.getActionControllerConstructorAccess().newInstance();
    }
}
