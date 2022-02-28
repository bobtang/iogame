package com.iohao.game.collect.gateway;

import com.iohao.little.game.net.server.core.ServerStartupConfig;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameGatewayBoot {
    public static void main(String[] args) {
        GameGatewayBoot gameGatewayBoot = new GameGatewayBoot();
        gameGatewayBoot.init();
    }

    public void init() {
        // 启动网关
        createGateway().startup();

        System.out.println("网关启动 ok!");
    }

    public ServerStartupConfig createGateway() {
        return new GameGateServerStartupConfig();
    }
}
