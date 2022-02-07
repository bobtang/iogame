package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameExternalBoot {
    public void init() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 启动内部逻辑服 连接网关服务器
                ClientStartupConfig gameExternalClientStartupConfig = new GameExternalClientStartupConfig();
                gameExternalClientStartupConfig.startup();
                System.out.println("external 启动内部逻辑服");
            }
        }, 1000);

        // 端口
        int port = GameConfig.externalPort;
        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        // websocket 方式连接
        builder.setExternalJoinEnum(ExternalJoinEnum.WEBSOCKET);

        // 构建游戏对外服
        ExternalServer externalServer = builder.build();
        // 启动游戏对外服
        externalServer.startup();

        System.out.println("external 启动游戏对外服 !");
    }

    public static void main(String[] args) {
        new GameExternalBoot().init();
        System.out.println("对外服务器启动 ok!");
    }
}
