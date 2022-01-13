package com.iohao.game.collect.external.tester;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.external.tester.server.TestExternalHandler;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
public class TestExternalServer {
    public void init() {

        // 启动对外服务器
        int port = GameConfig.externalPort;

        // 对外服务器 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        builder.registerChannelHandler("testExternalHandler", new TestExternalHandler());

        // 构建对外服务器
        ExternalServer externalServer = builder.build();
        externalServer.startup();
    }

    public static void main(String[] args) {
        new TestExternalServer().init();
        System.out.println("对外服务器启动 ok!");
    }
}
