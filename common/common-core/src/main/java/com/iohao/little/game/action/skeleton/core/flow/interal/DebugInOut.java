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
 * 统计actionCommand执行时间
 * <pre>
 *     该类不是线程安全的
 *
 *     see beetlsql DebugInterceptor
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
                ┏━━━━━ Debug [{className}.java] ━━━ [.({className}.java:1).{actionMethodName}]
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
        final var len = paramInfos.length;
        for (int i = 0; i < len; i++) {
            ActionCommand.ParamInfo paramInfo = paramInfos[i];
            Class<?> paramClazz = paramInfo.getParamClazz();

            // 这里可以使用策略模式 （但现在还不着急）
            if (BizContext.class.equals(paramClazz)) {
                continue;
            }

            if (AsyncContext.class.equals(paramClazz)) {
                continue;
            }

            if (ServerContext.class.equals(paramClazz)) {
                continue;
            }

            if (CmdInfo.class.equals(paramClazz)) {
                continue;
            }

            if (RequestMessage.class.equals(paramClazz)) {
                continue;
            }
            paramMap.put("paramName", paramInfo.getName());
            Object value = requestMessage.getData();
            if (value == null) {
                value = "null";
            }
            paramMap.put("paramData", value);

        }
    }

}
