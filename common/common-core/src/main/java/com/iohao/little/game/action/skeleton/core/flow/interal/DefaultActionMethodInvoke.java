package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodExceptionProcess;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInvoke;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;

/**
 * default DefaultActionMethodInvoke
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionMethodInvoke implements ActionMethodInvoke {

    @Override
    public Object invoke(FlowContext flowContext) {
        final ActionCommand actionCommand = flowContext.getActionCommand();
        final Object controller = flowContext.getActionController();
        final var pars = flowContext.getMethodParams();

        // 方法下标
        var actionMethodIndex = actionCommand.getActionMethodIndex();
        // 方法访问器
        MethodAccess actionMethodAccess = actionCommand.getActionMethodAccess();

        // 方法声明了异常的处理方式
        if (actionCommand.isThrowException()) {
            try {
                return actionMethodAccess.invoke(controller, actionMethodIndex, pars);
            } catch (Throwable e) {
                // 异常处理
                final BarSkeleton barSkeleton = flowContext.getBarSkeleton();
                ActionMethodExceptionProcess exceptionProcess = barSkeleton.getActionMethodExceptionProcess();
                // 把业务方法抛出的异常,交由异常处理类来处理
                return exceptionProcess.processException(e);
            }
        } else {
            // 方法没有声明会抛异常，走这里的逻辑, 少 try 一次
            return actionMethodAccess.invoke(controller, actionMethodIndex, pars);
        }
    }
}
