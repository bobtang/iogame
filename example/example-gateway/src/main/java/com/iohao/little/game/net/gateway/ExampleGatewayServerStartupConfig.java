package com.iohao.little.game.net.gateway;

import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.core.ServerStartupConfig;

public class ExampleGatewayServerStartupConfig implements ServerStartupConfig {
    @Override
    public BoltServer createBoltServer() {
        int port = ExampleCont.port;
        return new BoltServer(port);
    }
}
