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
package com.iohao.game.bolt.broker.client;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * BoltBrokerClient 构建与启动
 *
 * @author 渔民小镇
 * @date 2022-05-14
 */
@Slf4j
public class BrokerClientService {

    /**
     * 构建并启动 BoltBrokerClient
     *
     * @param config config
     * @return BoltBrokerClient
     */
    public BrokerClient start(BrokerClientStartup config) {

        BarSkeleton barSkeleton = config.createBarSkeleton();

        BrokerAddress brokerAddress = config.createBrokerAddress();

        BrokerClientBuilder builder = config
                .createBrokerClientBuilder()
                .barSkeleton(barSkeleton)
                .brokerAddress(brokerAddress);

        config.connectionEventProcessor(builder);
        config.registerUserProcessor(builder);

        return start(builder);
    }

    public BrokerClient start(BrokerClientBuilder builder) {
        BrokerClient brokerClient = builder.build();
        brokerClient.init();
        return brokerClient;
    }

    private BrokerClientService() {

    }

    public static BrokerClientService me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final BrokerClientService ME = new BrokerClientService();
    }
}
