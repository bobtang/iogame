package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ValidatorKit;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * action 方法参数解析器 actionCommand
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
public class ObjectActionMethodParamParser implements ActionMethodParamParser {
    private static final Object[] METHOD_PARAMS = new Object[0];

    @Override
    public Object[] listParam(final FlowContext flowContext) {

        ActionCommand actionCommand = flowContext.getActionCommand();
        if (!actionCommand.isHasMethodParam()) {
            return METHOD_PARAMS;
        }

        RequestMessage request = flowContext.getRequest();
        ResponseMessage response = flowContext.getResponse();

        final var paramInfos = actionCommand.getParamInfos();

        final var len = paramInfos.length;
        final var pars = new Object[len];

        for (int i = 0; i < len; i++) {
            ActionCommand.ParamInfo paramInfo = paramInfos[i];
            Class<?> paramClazz = paramInfo.getParamClazz();

            // 这里可以使用策略模式 （但现在还不着急）
            if (FlowContext.class.equals(paramClazz)) {
                // flow 上下文
                pars[i] = flowContext;
                continue;
            }

            pars[i] = request.getData();
            if (paramInfo.isValidator()) {
                String validateMsg = ValidatorKit.validate(pars[i]);
                response.setValidatorMsg(validateMsg);
            }

        }

        return pars;
    }
}
