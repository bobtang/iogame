package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;
import com.iohao.little.game.net.external.bootstrap.handler.ExternalHandlerWebsocket;

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
                // 启动连接网关服务器
                GameExternalClientStartupConfig gameExternalClientStartupConfig = new GameExternalClientStartupConfig();
                gameExternalClientStartupConfig.startup();
                System.out.println("external 启动连接网关服务器");
            }
        }, 1000);

        // 端口
        int port = GameConfig.externalPort;
        // 对外服务器 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        // websocket 方式连接
        builder.setExternalJoinEnum(ExternalJoinEnum.WEBSOCKET);
        // websocket 业务处理器
        builder.registerChannelHandler("externalHandler", new ExternalHandlerWebsocket());

        // 构建对外服务器
        ExternalServer externalServer = builder.build();
        // 启动对外服务器
        externalServer.startup();

        System.out.println("external 对外服务器启动 ok!");

    }

    public static void main(String[] args) {
        new GameExternalBoot().init();
        System.out.println("对外服务器启动 ok!");
    }
}
