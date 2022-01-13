package com.iohao.game.collect.external.tester.socket;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
public class TestExternalServerSocket {


    public static void main(String[] args) {
        new TestExternalServerSocket().init();
        System.out.println("对外服务器启动 ok!");
    }

    public void init() {
        // 端口
        int port = GameConfig.externalPort;
        // 对外服务器 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        // 业务处理器
        builder.registerChannelHandler("testExternalHandler", new TestExternalHandler());

        // 构建对外服务器
        ExternalServer externalServer = builder.build();
        // 启动对外服务器
        externalServer.startup();
    }
}
