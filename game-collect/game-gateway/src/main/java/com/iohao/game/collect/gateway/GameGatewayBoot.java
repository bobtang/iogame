package com.iohao.game.collect.gateway;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameGatewayBoot {
    public static void main(String[] args) {
        // 启动网关
        GameGateServerStartupConfig gameGateServerStartupConfig = new GameGateServerStartupConfig();
        gameGateServerStartupConfig.startup();
        System.out.println("网关启动 ok!");
    }
}
