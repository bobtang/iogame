package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;

/**
 * 游戏对外服
 *
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameExternalBoot {
    public void init() {
        // 启动游戏对外服
        createExternalServer().startup();

        System.out.println("external 启动游戏对外服 !");
    }

    public ExternalServer createExternalServer() {
        // 端口
        int port = GameConfig.externalPort;
        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port)
                // websocket 方式连接
                .setExternalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // 内部逻辑服 连接网关服务器
                .setExternalClientStartupConfig(new GameExternalClientStartupConfig());

        // 构建游戏对外服
        return builder.build();
    }

    public static void main(String[] args) {
        new GameExternalBoot().init();
        System.out.println("对外服务器启动 ok!");
    }
}
