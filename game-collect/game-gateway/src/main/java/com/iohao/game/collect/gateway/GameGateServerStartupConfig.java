package com.iohao.game.collect.gateway;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.core.ServerStartupConfig;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameGateServerStartupConfig implements ServerStartupConfig {
    @Override
    public BoltServer createBoltServer() {
        int port = GameConfig.port;
        return new BoltServer(port);
    }
}
