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
package com.iohao.game.collect.one;

import com.iohao.game.collect.GameLogicAll;
import com.iohao.game.collect.external.GameExternalBoot;
import com.iohao.game.collect.gateway.GameGateServerStartupConfig;
import com.iohao.little.game.net.external.simple.SimpleRunOne;

/**
 * 游戏启动
 *
 * @author 洛朱
 * @date 2022-01-13
 */
public class GameOne {
    public static void main(String[] args) {
        // 简单启动器
        SimpleRunOne simpleRunOne = new SimpleRunOne()
                // 网关服务器
                .setGatewayServer(new GameGateServerStartupConfig())
                // 对外服
                .setExternalServer(new GameExternalBoot().createExternalServer())
                // 逻辑服列表
                .setLogicServerList(new GameLogicAll().listLogicServer());

        // 启动对外服、网关、逻辑服
        simpleRunOne.startup();
    }
}
