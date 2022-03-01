package com.iohao.game.example.one;

import com.iohao.little.game.net.external.simple.SimpleHelper;

import java.util.List;

/**
 * @author 洛朱
 * @date 2022-02-24
 */
public class DemoApplication {
    public static void main(String[] args) {
        // 游戏对外服端口
        int port = 10100;

        // 游戏网关端口
        int gatewayPort = 10200;

        // 逻辑服
        DemoLogicServer demoLogicServer = new DemoLogicServer(gatewayPort);

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        SimpleHelper.run(port, gatewayPort, List.of(demoLogicServer));
    }
}
