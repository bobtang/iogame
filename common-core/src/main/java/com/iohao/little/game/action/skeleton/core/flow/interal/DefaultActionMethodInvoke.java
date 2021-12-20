package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodExceptionProcess;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInvoke;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * default DefaultActionMethodInvoke
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class DefaultActionMethodInvoke implements ActionMethodInvoke {

    @Override
    public Object invoke(ParamContext paramContext, ActionCommand actionCommand, Object controller, RequestMessage request, BarSkeleton barSkeleton) {
        // 解析参数
        ActionMethodParamParser paramParser = barSkeleton.getActionMethodParamParser();
        var pars = paramParser.listParam(paramContext, actionCommand, request);


        // 方法声明了异常的处理方式
        if (actionCommand.isHasThrowException()) {
            try {
                return actionCommand.getActionMethodAccess().invoke(controller, actionCommand.getActionMethodIndex(), pars);
            } catch (Throwable e) {
                e.printStackTrace();
                // 异常处理
                ActionMethodExceptionProcess exceptionProcess = barSkeleton.getActionMethodExceptionProcess();
                // 把业务方法抛出的异常,交由异常处理类来处理
                return exceptionProcess.processException(e);
            }
        } else {
            // 方法没有声明会抛异常，走这里的逻辑, 少 try 一次
            return actionCommand.getActionMethodAccess().invoke(controller, actionCommand.getActionMethodIndex(), pars);
        }
    }
}
