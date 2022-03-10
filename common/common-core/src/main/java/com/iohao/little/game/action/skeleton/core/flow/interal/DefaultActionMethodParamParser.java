/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
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
public class DefaultActionMethodParamParser implements ActionMethodParamParser {

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

            // 如果开启了验证
            if (paramInfo.isValidator()) {
                // 进行 JSR303+ 相关的验证
                String validateMsg = ValidatorKit.validate(pars[i]);
                response.setValidatorMsg(validateMsg);
            }

        }

        return pars;
    }
}
