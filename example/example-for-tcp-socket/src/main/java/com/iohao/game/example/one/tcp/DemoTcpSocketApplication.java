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
package com.iohao.game.example.one.tcp;

import com.iohao.game.example.one.server.DemoLogicServer;
import com.iohao.little.game.net.client.core.ClientStartup;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;
import com.iohao.little.game.net.external.config.ExternalOtherConfig;
import com.iohao.little.game.net.external.simple.SimpleHelper;
import com.iohao.little.game.net.external.simple.SimpleRunOne;
import com.iohao.little.game.net.server.core.ServerStartup;

import java.util.List;

/**
 * tcp socket 服务器启动类
 *
 * @author 洛朱
 * @date 2022-04-13
 */
public class DemoTcpSocketApplication {
    public static void main(String[] args) {
        // 注意，这个是临时测试用的，设置为 false 表示不用登录就可以访问逻辑服的方法
        ExternalOtherConfig.verifyIdentity = false;

        // 游戏对外服端口
        int port = 10100;

        // 游戏网关端口
        int gatewayPort = 10200;

        // 对外服 webSocket 方式连接 （对应的测试类是 DemoWebsocketClient 客户端）
        ExternalJoinEnum externalJoinEnum = ExternalJoinEnum.WEBSOCKET;
        // 对外服 tcp socket 方式连接 （对应的测试类是 DemoSocketClient 客户端）
        externalJoinEnum = ExternalJoinEnum.SOCKET;

        // 对外服
        ExternalServer externalServer = SimpleHelper.createExternalServer(externalJoinEnum, port, gatewayPort);
        // 网关服务器
        ServerStartup gatewayServer = () -> new BoltServer(gatewayPort);
        // 逻辑服列表
        List<ClientStartup> logicServer = List.of(
                new DemoLogicServer(gatewayPort)
        );

        new SimpleRunOne()
                // 网关服务器
                .setGatewayServer(gatewayServer)
                // 对外服
                .setExternalServer(externalServer)
                // 逻辑服列表
                .setLogicServerList(logicServer)
                // 启动 对外服、网关、逻辑服
                .startup();

        /*
         * 该示例文档地址
         * https://www.yuque.com/iohao/game/ywe7uc
         */
    }

}
