package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.ActionAfter;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.core.flow.InOutContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * 默认的 action 命令流程执行器
 * <pre>
 *     编排业务框架处理业务类的流程
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
public final class DefaultActionCommandFlowExecute implements ActionCommandFlowExecute {

    public void execute(final ParamContext paramContext
            , final ActionCommand actionCommand
            , final RequestMessage request
            , final BarSkeleton barSkeleton) {

        // 1 ---- ActionController 工厂
        var factoryBean = barSkeleton.getActionControllerFactoryBean();
        var controller = factoryBean.getBean(actionCommand);

        // 解析参数器
        ActionMethodParamParser paramParser = barSkeleton.getActionMethodParamParser();
        // 业务方法参数列表
        var pars = paramParser.listParam(paramContext, actionCommand, request);

        // inout 上下文
        InOutContext inOutContext = getInOutContext(paramContext, actionCommand, controller, request, barSkeleton, pars);
        // 2 ---- fuck前 在调用控制器对应处理方法前, 执行inout的in.
        fuckIn(inOutContext, barSkeleton);

        // 3 ---- fuck中 开始执行控制器方法, 这是真正处理客户端请求的逻辑.
        var actionMethodInvoke = barSkeleton.getActionMethodInvoke();
        // 得到业务类的返回结果
        var result = actionMethodInvoke.invoke(paramContext, actionCommand, controller, request, barSkeleton, pars);

        // 4 ---- wrap result 结果包装器
        var actionMethodResultWrap = barSkeleton.getActionMethodResultWrap();
        // 响应
        ResponseMessage response = actionMethodResultWrap.wrap(actionCommand, request, result);

        // 5 ---- after 一般用于响应数据到 请求端
        ActionAfter<RequestMessage, ResponseMessage> actionAfter = barSkeleton.getActionAfter();
        actionAfter.execute(paramContext, actionCommand, request, response);

        // 6 ---- fuck后 在调用控制器对应处理方法结束后, 执行inout的out.
        fuckOut(inOutContext, response, barSkeleton);
    }

    private void fuckIn(InOutContext inOutContext, BarSkeleton barSkeleton) {

        if (barSkeleton.isOpenIn()) {
            for (ActionMethodInOut inOut : barSkeleton.getInOuts()) {
                inOut.fuckIn(inOutContext);
            }
        }
    }

    private void fuckOut(InOutContext inOutContext
            , ResponseMessage response
            , BarSkeleton barSkeleton) {

        if (barSkeleton.isOpenOut()) {
            inOutContext.setResponseMessage(response);

            for (ActionMethodInOut inOut : barSkeleton.getInOuts()) {
                inOut.fuckOut(inOutContext);
            }
        }
    }

    private InOutContext getInOutContext(ParamContext paramContext
            , ActionCommand actionCommand
            , Object controller
            , RequestMessage request
            , BarSkeleton barSkeleton
            , Object[] methodParams) {

        if (barSkeleton.isOpenIn() || barSkeleton.isOpenOut()) {
            return new InOutContext()
                    .setParamContext(paramContext)
                    .setActionCommand(actionCommand)
                    .setActionController(controller)
                    .setRequestMessage(request)
                    .setMethodParams(methodParams)
                    ;
        }
        return null;
    }

}
