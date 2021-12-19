package com.iohao.little.game.net.gateway;

import lombok.extern.slf4j.Slf4j;

/**
 * <BR>
 */
@Slf4j
public class GatewayServer {

    public void init() {
        var gatewayServerStartupConfig = new GatewayServerStartupConfig();
        gatewayServerStartupConfig.startup();
    }

    public static void main(String[] args) {
        // 启动网关
        GatewayServer gatewayServe = new GatewayServer();
        gatewayServe.init();
    }


}
