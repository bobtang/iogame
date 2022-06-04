/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.bolt.broker.boot.monitor.service;

import com.iohao.game.bolt.broker.boot.monitor.message.MonitorCollectMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-06-04
 */
@Slf4j
public class MonitorAnalysis {

    public void analyse(MonitorCollectMessage collectMessage) {
        // TODO:  信息收集入口
        //  see MonitorExtRegion 接收收集信息的入口（上报信息）
        //  see MonitorService  通过 oshi 监控信息相关服务类（上报信息）
        log.info("接收来自各逻辑服的响应 : \n{}", collectMessage.toJsonPretty());
    }

    private MonitorAnalysis() {

    }

    public static MonitorAnalysis me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final MonitorAnalysis ME = new MonitorAnalysis();
    }
}
