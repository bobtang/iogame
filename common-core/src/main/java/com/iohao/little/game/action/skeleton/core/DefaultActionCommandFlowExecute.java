package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

import java.util.Objects;

public final class DefaultActionCommandFlowExecute implements ActionCommandFlowExecute {

    public void execute(final ParamContext paramContext, final ActionCommand actionCommand, final RequestMessage request, final BarSkeleton barSkeleton) {
        // 1 - ActionController 工厂
        var factoryBean = barSkeleton.getActionControllerFactoryBean();
        var controller = factoryBean.getBean(actionCommand);

        // 2 - fuck前 在调用控制器对应处理方法前, 执行inout的in.
        fuckIn(paramContext, actionCommand, controller, request, barSkeleton);

        // aware 注入
//        barSkeleton.getAwareExecute().executeAware(controller, channelContext, request, actionCommand, barSkeleton);

        // 3 - fuck中 开始执行控制器方法, 这是真正处理客户端请求的逻辑.
        var actionMethodInvoke = barSkeleton.getActionMethodInvoke();
        var result = actionMethodInvoke.invoke(paramContext, actionCommand, controller, request, barSkeleton);

        ResponseMessage response = null;
        // 只将非 void 方法的值和有异常的情况, 才写入到 channel 中
        var actionMethodReturnInfo = actionCommand.getActionMethodReturnInfo();
        if (Objects.nonNull(result) || Void.TYPE != actionMethodReturnInfo.getReturnTypeClazz()) {
            // 结果包装器
            var actionMethodResultWrap = barSkeleton.getActionMethodResultWrap();
            // 4 - wrap result
            response = actionMethodResultWrap.wrap(actionCommand, request, result);

            // 5 - after 一般用于响应数据到 请求端
            ActionAfter<RequestMessage, ResponseMessage> actionAfter = barSkeleton.getActionAfter();
            actionAfter.execute(paramContext, actionCommand, request, response);
        }

        // 6 - fuck后 在调用控制器对应处理方法结束后, 执行inout的out.
        fuckOut(paramContext, actionCommand, controller, request, response, barSkeleton);
    }

    private void fuckIn(ParamContext paramContext
            , ActionCommand actionCommand
            , Object controller
            , RequestMessage request
            , BarSkeleton barSkeleton) {

        if (barSkeleton.isOpenIn()) {
            barSkeleton.getInOuts().forEach(inOut -> inOut.fuckIn(paramContext, actionCommand, controller, request));
        }
    }


    private void fuckOut(ParamContext paramContext
            , ActionCommand actionCommand
            , Object controller
            , RequestMessage request
            , ResponseMessage response
            , BarSkeleton barSkeleton) {

        if (barSkeleton.isOpenOut()) {
            barSkeleton.getInOuts().forEach(inOut -> inOut.fuckOut(paramContext, actionCommand, controller, request, response));
        }
    }

}
