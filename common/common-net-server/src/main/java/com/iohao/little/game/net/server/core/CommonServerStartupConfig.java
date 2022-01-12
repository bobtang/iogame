package com.iohao.little.game.net.server.core;

import com.alipay.remoting.ConnectionEventType;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.processor.*;
import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.broadcast.internal.GateBroadcastMessageListenerWidget;
import com.iohao.little.game.widget.broadcast.internal.GateInternalMessageQueueWidget;
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
        // 消息广播-配置项
        MessageQueueConfigWidget messageQueueConfigWidget = new MessageQueueConfigWidget();
        // 消息广播-小部件
        MessageQueueWidget messageQueueWidget = null;
        // 消息广播-小部件 - 使用内网的实现 (也可以换成 redis[Redisson， Lettuce], MQ[Apache Pulsar, RocketMQ]等)

        // 消息广播-小部件 - 使用实现 内网
        messageQueueWidget = new GateInternalMessageQueueWidget(messageQueueConfigWidget);

        // 消息广播-小部件 - 使用实现 redis[Redisson]
//        messageQueueWidget = new RedissonMessageQueueWidget(messageQueueConfigWidget);

        // 添加发布订阅消息处理类
        messageQueueWidget.addMessageListener(new GateBroadcastMessageListenerWidget());

        // 添加到部件管理中
        widgetComponents.option(MessageQueueWidget.class, messageQueueWidget);
    }

    public void connectionEventProcessor(BoltServer boltServer) {
        GateConnectionEventProcessor serverConnectProcessor = new GateConnectionEventProcessor();
        GateCloseConnectionEventProcessor serverDisConnectProcessor = new GateCloseConnectionEventProcessor();

        serverConnectProcessor.setServer(boltServer);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);
    }

    public void registerUserProcessor(BoltServer boltServer) {
        // 处理 - 模块注册
        var registerModuleProcessor = new GateRegisterModuleMessageAsyncUserProcessor();
        boltServer.registerUserProcessor(registerModuleProcessor);

        // 处理 - 内部模块消息的转发
        var innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcess();
        boltServer.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        // 处理 - 接收真实用户的请求，把请求转发到逻辑服
        var externalMessageProcessor = new GateInnerExternalMessageAsyncUserProcessor();
        boltServer.registerUserProcessor(externalMessageProcessor);

        // 处理 - 发布订阅
        WidgetComponents widgetComponents = boltServer.getWidgetComponents();
        var gateBroadcastMessageAsyncUserProcess = new GateBroadcastMessageAsyncUserProcess(widgetComponents);
        boltServer.registerUserProcessor(gateBroadcastMessageAsyncUserProcess);
    }

}
