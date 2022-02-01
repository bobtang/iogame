package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ValidatorKit;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodParamParser;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.ProtoKit;

import java.util.Objects;

/**
 * pb 参数解析器
 *
 * @author 洛朱
 * @date 2022-01-12
 */
public class ProtoActionMethodParamParser implements ActionMethodParamParser {

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

            // 业务参数
            byte[] dataContent = request.getDataContent();

            if (Objects.isNull(dataContent)) {
                continue;
            }

            // 把字节解析成 pb 对象
            pars[i] = ProtoKit.parseProtoByte(dataContent, paramClazz);
            request.setData(pars[i]);

            if (paramInfo.isValidator()) {
                String validateMsg = ValidatorKit.validate(pars[i]);
                response.setValidatorMsg(validateMsg);
            }

        }

        return pars;
    }
}
