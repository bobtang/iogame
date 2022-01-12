package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class ExternalBoot {
    public void init() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 启动连接网关服务器
                ExternalClientStartupConfig externalClientStartupConfig = new ExternalClientStartupConfig();
                externalClientStartupConfig.startup();
                System.out.println("external 启动连接网关服务器");
            }
        }, 1000);

        // 启动对外服务器
        int port = GameConfig.externalPort;
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        ExternalServer externalServer = builder.build();
        externalServer.startup();
        System.out.println("external server OK!");


    }

    public static void main(String[] args) {
        new ExternalBoot().init();
        System.out.println("对外服务器启动 ok!");
    }
}
