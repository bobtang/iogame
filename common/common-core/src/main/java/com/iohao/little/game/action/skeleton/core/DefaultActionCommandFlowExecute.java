package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.*;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

import java.util.HashMap;

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

        // flow 上下文
        FlowContext flowContext = createFlowContext(paramContext, actionCommand, request, barSkeleton);

        // 1 ---- ActionController 工厂
        var factoryBean = barSkeleton.getActionControllerFactoryBean();
        var controller = factoryBean.getBean(actionCommand);
        // 业务 actionController
        flowContext.setActionController(controller);

        // 2 ---- fuck前 在调用控制器对应处理方法前, 执行inout的in.
        fuckIn(flowContext);

        // 在这里有错误码，一般是业务参数验证得到的错误 （既开启了业务框架的验证）
        if (notError(flowContext)) {

            // 3 ---- fuck中 开始执行控制器方法, 这是真正处理客户端请求的逻辑.
            var actionMethodInvoke = barSkeleton.getActionMethodInvoke();
            // 得到业务类的返回结果
            var result = actionMethodInvoke.invoke(flowContext);
            flowContext.setMethodResult(result);

            // 4 ---- wrap result 结果包装器
            var actionMethodResultWrap = barSkeleton.getActionMethodResultWrap();
            // 响应
            actionMethodResultWrap.wrap(flowContext);
        }

        // 5 ---- after 一般用于响应数据到 请求端
        // TODO: 2022/1/17 验证
        ActionAfter actionAfter = barSkeleton.getActionAfter();
        actionAfter.execute(flowContext);

        // 6 ---- fuck后 在调用控制器对应处理方法结束后, 执行inout的out.
        fuckOut(flowContext);
    }

    private void fuckIn(FlowContext flowContext) {

        BarSkeleton barSkeleton = flowContext.getBarSkeleton();

        if (barSkeleton.isOpenIn()) {
            for (ActionMethodInOut inOut : barSkeleton.getInOuts()) {
                inOut.fuckIn(flowContext);
            }
        }
    }

    private void fuckOut(FlowContext flowContext) {

        BarSkeleton barSkeleton = flowContext.getBarSkeleton();

        if (barSkeleton.isOpenOut()) {
            for (ActionMethodInOut inOut : barSkeleton.getInOuts()) {
                inOut.fuckOut(flowContext);
            }
        }
    }

    private FlowContext createFlowContext(ParamContext paramContext
            , ActionCommand actionCommand
            , RequestMessage request
            , BarSkeleton barSkeleton) {

        // 创建响应对象
        ResponseMessageCreate responseMessageCreate = barSkeleton.getResponseMessageCreate();
        ResponseMessage responseMessage = responseMessageCreate.createResponseMessage();
        responseMessage.setCmdInfo(actionCommand.getCmdInfo());

        FlowContext flowContext = new FlowContext()
                .setBarSkeleton(barSkeleton)
                .setParamContext(paramContext)
                .setActionCommand(actionCommand)
                .setRequest(request)
                .setResponse(responseMessage);

        if (barSkeleton.isOpenIn() || barSkeleton.isOpenOut()) {
            flowContext.setAttr(new HashMap<>());
        }

        // 解析参数器
        ActionMethodParamParser paramParser = barSkeleton.getActionMethodParamParser();
        // 业务方法参数列表 , 并验证
        var pars = paramParser.listParam(flowContext);
        // 业务方法参数 save to flowContext
        flowContext.setMethodParams(pars);

        return flowContext;
    }

    /**
     * 响应是否的错
     *
     * @param flowContext flow 上下文
     * @return true 没有错误
     */
    private boolean notError(FlowContext flowContext) {
        return !flowContext.getResponse().hasError();
    }

}
