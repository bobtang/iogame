package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class ExternalBoot {
    public void init() {
        // 启动对外服务器
        int port = GameConfig.port;
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        ExternalServer externalServer = builder.build();
        externalServer.startup();
        System.out.println("external server OK!");

        // 启动连接网关服务器
        ExternalClientStartupConfig externalClientStartupConfig = new ExternalClientStartupConfig();
        externalClientStartupConfig.startup();
    }
}
