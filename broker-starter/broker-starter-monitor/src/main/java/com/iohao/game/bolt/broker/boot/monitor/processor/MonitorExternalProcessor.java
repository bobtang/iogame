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
package com.iohao.game.bolt.broker.boot.monitor.processor;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.bolt.broker.client.external.session.UserSessions;
import com.iohao.game.bolt.broker.core.aware.BrokerClientAware;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2022-05-30
 */
@Slf4j
public class MonitorExternalProcessor implements BrokerClientAware {
    @Setter
    BrokerClient brokerClient;

    private void a() {
        // cpu相关的（使用率、温度）、内存使用率、磁盘（容量、IO）、
        // 硬盘SMART健康状态、逻辑服的数量、
        // 对外服的在线人数、action相关的（action数量、action信息）。

        BarSkeleton barSkeleton = brokerClient.getBarSkeleton();

        // 当前逻辑服 action 数据
        List<Integer> cmdMergeList = barSkeleton.getActionCommandRegions().listCmdMerge();

        // 当前在线人数
        long countOnline = UserSessions.me().countOnline();
        System.out.println("hello");
    }

    public static void main(String[] args) {

    }
}
