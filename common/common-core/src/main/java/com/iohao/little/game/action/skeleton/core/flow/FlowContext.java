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
package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.common.kit.attr.AttrOptionDynamic;
import com.iohao.little.game.common.kit.attr.AttrOptions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * 业务框架 flow 上下文
 * <pre>
 *     生命周期存在于这一次的 flow 过程
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-21
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlowContext implements AttrOptionDynamic {
    /** 动态属性 */
    final AttrOptions options = new AttrOptions();
    /** 业务框架 */
    BarSkeleton barSkeleton;
    /** 参数上下文 */
    ParamContext paramContext;
    /** command */
    ActionCommand actionCommand;
    /** 控制器类对象 */
    Object actionController;
    /** 请求对象 */
    RequestMessage request;
    /** 响应对象, 在InOut.fuckOut 时才会有值 */
    ResponseMessage response;
    /** 业务方法参数 */
    Object[] methodParams;
    /** 业务方法的返回值 */
    Object methodResult;
    /** userId */
    long userId;
    /** true 业务方法有异常 */
    boolean error;

    @SuppressWarnings("unchecked")
    public <T extends ParamContext> T getParamContext() {
        return (T) this.paramContext;
    }
}
