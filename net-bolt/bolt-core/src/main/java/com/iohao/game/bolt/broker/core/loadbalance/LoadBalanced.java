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
package com.iohao.game.bolt.broker.core.loadbalance;

import com.iohao.game.bolt.broker.core.BoltConnection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 负载均衡
 *
 * @author 渔民小镇
 * @date 2022-05-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoadBalanced {
    /** 随机选择器 */
    RandomElementSelector<BoltConnection> randomElementSelector;
    Map<String, BoltConnection> activeSockets;

    /**
     * unhealthy uris
     */
    final Set<String> unHealthyUriSet = new HashSet<>();
    long lastHealthCheckTimeStamp = System.currentTimeMillis();
    long lastRefreshTimeStamp = System.currentTimeMillis();

    /**
     * 健康检查时间间隔秒
     */
    private static final int HEALTH_CHECK_INTERVAL_SECONDS = 15;
    /**
     * 由于连接错误而重试的次数，间隔为5秒
     */
    private final int retryCount = 12;

    void connect(String uri) {

    }
}
