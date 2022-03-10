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
package com.iohao.little.game.net.external.bootstrap.initializer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@Setter
public class ExternalChannelInitializerCallbackOption {
    /** 心跳 */
    ChannelHandler idleHandler;
    /** 心跳时间 */
    int idleTime = 10;

    /** user processors of rpc server */
    Map<String, ChannelHandler> channelHandlerProcessors;
    /** 默认数据包最大 1MB */
    int packageMaxSize = 1024 * 1024;
    /** http 升级 websocket 协议地址 */
    String websocketPath = "/websocket";

    void idleHandler(ChannelPipeline pipeline) {
        // 心跳处理
        if (Objects.nonNull(this.idleHandler)) {
            pipeline.addLast("idleStateHandler",
                    new IdleStateHandler(0, 0, this.idleTime, TimeUnit.MILLISECONDS));
            pipeline.addLast("idleHandler", this.idleHandler);
        }
    }

    void channelHandlerProcessors(ChannelPipeline pipeline) {
        if (Objects.nonNull(this.channelHandlerProcessors)) {
            for (Map.Entry<String, ChannelHandler> entry : this.channelHandlerProcessors.entrySet()) {
                pipeline.addLast(entry.getKey(), entry.getValue());
            }
        }
    }
}
