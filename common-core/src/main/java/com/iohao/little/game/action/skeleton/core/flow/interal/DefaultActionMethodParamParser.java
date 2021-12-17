package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.iohao.little.game.action.skeleton.core.*;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * action 方法参数解析器 actionCommand
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public class DefaultActionMethodParamParser implements ActionMethodParamParser {
    private static final Object[] METHOD_PARAMS = new Object[0];

    @Override
    public Object[] listParam(final ParamContext paramContext1, final ActionCommand actionCommand, final RequestMessage request) {

        if (!actionCommand.isHasMethodParam()) {
            return METHOD_PARAMS;
        }

        final var paramContext = (DefaultParamContext) paramContext1;
        final var paramInfos = actionCommand.getParamInfos();

        final var len = paramInfos.length;
        final var pars = new Object[len];

        for (int i = 0; i < len; i++) {
            ActionCommand.ParamInfo paramInfo = paramInfos[i];
            Class<?> paramClazz = paramInfo.getParamClazz();

            // 这里可以使用策略模式 （但现在还不着急）
            if (BizContext.class.equals(paramClazz)) {
                pars[i] = paramContext.getBizCtx();
                continue;
            }

            if (AsyncContext.class.equals(paramClazz)) {
                pars[i] = paramContext.getAsyncCtx();
                continue;
            }

            if (ServerContext.class.equals(paramClazz)) {
                pars[i] = paramContext.getServerContext();
                continue;
            }

            if (CmdInfo.class.equals(paramClazz)) {
                pars[i] = request.getCmdInfo();
                continue;
            }

            if (RequestMessage.class.equals(paramClazz)) {
                pars[i] = request;
                continue;
            }

            pars[i] = request.getData();
        }

        return pars;
    }
}
