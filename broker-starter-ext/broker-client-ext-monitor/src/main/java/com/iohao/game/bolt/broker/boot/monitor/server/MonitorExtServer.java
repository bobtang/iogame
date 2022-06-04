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
package com.iohao.game.bolt.broker.boot.monitor.server;

import com.iohao.game.action.skeleton.core.commumication.ProcessorContext;
import com.iohao.game.action.skeleton.protocol.processor.ExtRequestMessage;
import com.iohao.game.bolt.broker.boot.monitor.processor.ExtResponseMessageClientProcessor;
import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.client.ext.AbstractExtBrokerClientStartup;
import com.iohao.game.bolt.broker.client.processor.ExtRequestMessageClientProcessor;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.common.kit.ExecutorKit;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 监控逻辑服
 *
 * @author 渔民小镇
 * @date 2022-05-30
 */
@Slf4j
public class MonitorExtServer extends AbstractExtBrokerClientStartup {

    public static void main(String[] args) {
        // 启动监控逻辑服
        BrokerClientApplication.start(new MonitorExtServer());
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {

        BrokerClientBuilder builder = BrokerClient.newBuilder();

        builder
                .id("MonitorExtServer")
                .appName("监控逻辑服")
                .tag("monitor")
        ;

        // 移除一个当前逻辑服不需要的处理器
        builder.removeUserProcessor(ExtRequestMessageClientProcessor.class);
        // 添加一个处理器
        builder.registerUserProcessor(ExtResponseMessageClientProcessor::new);

        return builder;
    }

    @Override
    public void startupSuccess(BrokerClient brokerClient) {

        // 逻辑服启动成功后
        LongAdder longAdder = new LongAdder();
        ExecutorKit.newSingleScheduled(MonitorExtServer.class.getName()).scheduleAtFixedRate(() -> {
            longAdder.increment();

            log.info("通知各逻辑服上报信息 {}", longAdder.longValue());

            ExtRequestMessage extRequestMessage = new ExtRequestMessage();
            ProcessorContext processorContext = brokerClient.getProcessorContext();
            processorContext.invokeOneway(extRequestMessage);

        }, 3, 10, TimeUnit.SECONDS);
    }
}
