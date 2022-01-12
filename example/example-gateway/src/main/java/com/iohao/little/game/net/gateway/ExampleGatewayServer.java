package com.iohao.little.game.net.gateway;

import lombok.extern.slf4j.Slf4j;

/**
 * <BR>
 */
@Slf4j
public class ExampleGatewayServer {

    public void init() {
        var gatewayServerStartupConfig = new ExampleGatewayServerStartupConfig();
        gatewayServerStartupConfig.startup();
    }

    public static void main(String[] args) {
        // 启动网关
        ExampleGatewayServer gatewayServe = new ExampleGatewayServer();
        gatewayServe.init();
    }


}
