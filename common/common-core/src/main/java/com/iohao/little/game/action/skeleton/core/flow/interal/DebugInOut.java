package com.iohao.little.game.action.skeleton.core.flow.interal;

import cn.hutool.core.util.StrUtil;
import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.ServerContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.core.flow.InOutContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * debug info 开发阶段推荐
 *
 * <pre>
 * 日志输出
 *
 * ┏━━━━━ Debug [ActivityAction.java] ━━━ [.(ActivityAction.java:1).hello]
 * ┣ 参数: active : Active(id=101, name=塔姆)
 * ┣ 响应: 塔姆, I'm here
 * ┣ 时间: 1 ms (业务方法总耗时)
 * ┗━━━━━ Debug [ActivityAction.java] ━━━
 *
 * 参数 :  通常是游戏前端传入的值
 * 响应：通常是业务方法返回的值 （游戏后端人员编写的业务）
 * 时间：执行业务方法总耗时
 * Debug [ActivityAction.java] ：表示业务方法是在这个类运行的
 * (ActivityAction.java:1).hello ：表示运行的业务方法名是 hello
 *
 * 有了以上信息，游戏开发者可以很快的定位问题。
 * 如果没有可视化的信息，开发中会浪费很多时间在前后端的沟通上。
 * 问题包括：
 * ● 是否传参问题 （游戏前端说传了）
 * ● 是否响应问题（游戏后端说返回了）
 * ● 业务执行时长问题 （游戏前端说没收到响应， 游戏后端说早就响应了）
 *
 * see beetlsql DebugInterceptor
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
public class DebugInOut implements ActionMethodInOut {

    String timeKey = "ExecuteTimeInOutStartTime";

    @Override
    public void fuckIn(InOutContext inOutContext) {
        inOutContext.put(timeKey, System.currentTimeMillis());
    }

    @Override
    public void fuckOut(InOutContext inOutContext) {

        long ms = System.currentTimeMillis() - inOutContext.getLong(timeKey);

        String template = """
                ┏━━━━━ Debug [{className}.java] ━━━ [.({className}.java:1).{actionMethodName}] ━━━ {cmdInfo}
                ┣ 参数: {paramName} : {paramData}
                ┣ 响应: {returnData}
                ┣ 时间: {time} ms (业务方法总耗时)
                ┗━━━━━ Debug [{className}.java] ━━━
                """;

        ActionCommand actionCommand = inOutContext.getActionCommand();
        Class<?> cc = actionCommand.getActionControllerClazz();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("className", cc.getSimpleName());
        paramMap.put("actionMethodName", actionCommand.getActionMethodName());
        paramMap.put("time", ms);

        paramMap.put("paramName", "");
        paramMap.put("paramData", "");
        paramMap.put("returnData", "");

        methodResponseData(inOutContext, paramMap);
        methodRequestParam(inOutContext, paramMap);

        if (actionCommand.getActionMethodReturnInfo().getReturnTypeClazz() == Void.TYPE) {
            paramMap.put("returnData", "void");
        }

        // 路由信息
        CmdInfo cmdInfo = inOutContext.getRequestMessage().getCmdInfo();
        paramMap.put("cmdInfo", cmdInfo);

        String message = StrUtil.format(template, paramMap);
        System.out.println(message);
    }

    private void methodResponseData(InOutContext inOutContext, Map<String, Object> paramMap) {
        ResponseMessage responseMessage = inOutContext.getResponseMessage();
        Object responseMessageData = responseMessage.getData();
        if (Objects.isNull(responseMessageData)) {
            responseMessageData = "null";
        }

        paramMap.put("returnData", responseMessageData);

    }

    private void methodRequestParam(InOutContext inOutContext, Map<String, Object> paramMap) {
        ActionCommand actionCommand = inOutContext.getActionCommand();
        if (!actionCommand.isHasMethodParam()) {
            return;
        }

        RequestMessage requestMessage = inOutContext.getRequestMessage();
        final var paramInfos = actionCommand.getParamInfos();

        for (ActionCommand.ParamInfo paramInfo : paramInfos) {
            Class<?> paramClazz = paramInfo.getParamClazz();

            // 这里可以使用策略模式 （但现在还不着急）
            if (BizContext.class.equals(paramClazz)
                    || AsyncContext.class.equals(paramClazz)
                    || ServerContext.class.equals(paramClazz)
                    || CmdInfo.class.equals(paramClazz)
                    || RequestMessage.class.equals(paramClazz)
            ) {
                continue;
            }

            paramMap.put("paramName", paramInfo.getName());

            Object value = requestMessage.getData();
            if (Objects.isNull(value)) {
                value = "null";
            }

            paramMap.put("paramData", value);

        }
    }

}
