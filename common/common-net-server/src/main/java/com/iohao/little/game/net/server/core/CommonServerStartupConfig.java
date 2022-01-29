package com.iohao.little.game.net.server.core;

import com.alipay.remoting.ConnectionEventType;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.processor.*;
import com.iohao.little.game.widget.config.WidgetComponents;
import lombok.experimental.UtilityClass;

/**
 * 提供一些默认配置
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@UtilityClass
public class CommonServerStartupConfig {
    public void configBroadcastWidgetComponents(WidgetComponents widgetComponents) {

    }

    public void connectionEventProcessor(BoltServer boltServer) {
        GateConnectionEventProcessor serverConnectProcessor = new GateConnectionEventProcessor();
        GateCloseConnectionEventProcessor serverDisConnectProcessor = new GateCloseConnectionEventProcessor();

        serverConnectProcessor.setServer(boltServer);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);
    }

    public void registerUserProcessor(BoltServer boltServer) {
        // 处理 - 广播
        var gateBroadcastMessageAsyncUserProcess = new GateBroadcastMessageAsyncUserProcessor(boltServer);
        boltServer.registerUserProcessor(gateBroadcastMessageAsyncUserProcess);

        // 处理 - 模块注册
        var registerModuleProcessor = new GateRegisterModuleMessageAsyncUserProcessor(boltServer);
        boltServer.registerUserProcessor(registerModuleProcessor);

        // 处理 - 内部模块消息的转发
        var innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcessor();
        boltServer.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        // 处理 - 改变用户 id -- external server
        var gateChangeUserIdMessageAsyncUserProcess = new GateChangeUserIdMessageAsyncUserProcessor(boltServer);
        boltServer.registerUserProcessor(gateChangeUserIdMessageAsyncUserProcess);

        // 处理 - (接收真实用户的请求) 把对外服的请求转发到逻辑服
        var externalMessageProcessor = new GateExternalRequestMessageAsyncUserProcessor(boltServer);
        boltServer.registerUserProcessor(externalMessageProcessor);

        // 处理 - （响应真实用户的请求）把逻辑服的响应转发到对外服
        var responseMessageAsyncUserProcessor = new GateResponseMessageAsyncUserProcessor(boltServer);
        boltServer.registerUserProcessor(responseMessageAsyncUserProcessor);
    }

}
