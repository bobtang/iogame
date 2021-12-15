package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

public class DefaultActionMethodParamParser implements ActionMethodParamParser {
    private static final Object[] METHOD_PARAMS = new Object[0];

    @Override
    public Object[] listParam(final ParamContext paramContext1, final ActionCommand actionCommand, final RequestMessage request) {

        if (!actionCommand.isHasMethodParam()) {
            return METHOD_PARAMS;
        }

        var paramContext = (DefaultParamContext) paramContext1;
        var paramInfos = actionCommand.getParamInfos();

        final var len = paramInfos.length;
        final var pars = new Object[len];

        for (int i = 0; i < len; i++) {
            ActionCommand.ParamInfo paramInfo = paramInfos[i];
            Class<?> paramClazz = paramInfo.getParamClazz();
            if (BizContext.class.equals(paramClazz)) {
                pars[i] = paramContext.getBizCtx();
            } else if (AsyncContext.class.equals(paramClazz)) {
                pars[i] = paramContext.getAsyncCtx();
            } else if (CmdInfo.class.equals(paramClazz)) {
                pars[i] = request.getCmdInfo();
            } else {
                pars[i] = request.getData();
            }
        }

        return pars;
    }
}
