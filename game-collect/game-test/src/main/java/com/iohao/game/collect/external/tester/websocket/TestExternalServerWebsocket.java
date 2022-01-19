package com.iohao.game.collect.external.tester.websocket;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
public class TestExternalServerWebsocket {


    public static void main(String[] args) {
        new TestExternalServerWebsocket().init();
        System.out.println("对外服务器启动 ok!");
    }

    public void init() {

        // 端口
        int port = GameConfig.externalPort;
        // 对外服务器 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        // websocket 方式连接
        builder.setExternalJoinEnum(ExternalJoinEnum.WEBSOCKET);
        // websocket 业务处理器
        builder.registerChannelHandler("TestExternalBizHandler", new TestExternalBizHandler());

        // 构建对外服务器
        ExternalServer externalServer = builder.build();
        // 启动对外服务器
        externalServer.startup();
    }
}
