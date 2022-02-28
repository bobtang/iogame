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
