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
package com.iohao.little.game.action.skeleton.core;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import lombok.Data;

/**
 * 参数上下文
 * <pre>
 *     bolt 中获取的一些上下文
 *     目前只使用了 AsyncContext 来推送数据
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Data
public class DefaultParamContext implements ParamContext {
    /** bolt biz 上下文 */
    BizContext bizCtx;
    /** bolt async 上下文 */
    AsyncContext asyncCtx;
    /** 当前项目启动的服务上下文 */
    ServerContext serverContext;
}
