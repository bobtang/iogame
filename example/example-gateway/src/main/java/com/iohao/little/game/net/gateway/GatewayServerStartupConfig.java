package com.iohao.little.game.net.gateway;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.Configs;
import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.BoltServer;
import com.iohao.little.game.net.gateway.common.*;
import com.iohao.little.game.net.gateway.core.ServerStartupConfig;
import com.iohao.little.game.net.gateway.widget.broadcast.BroadcastMessageListenerWidget;
import com.iohao.little.game.widget.config.WidgetComponents;
import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.broadcast.internal.InternalMessageQueueWidget;

public class GatewayServerStartupConfig implements ServerStartupConfig {
    @Override
    public BoltServer createBoltServer() {
        // 开启 bolt 重连
        System.setProperty(Configs.CONN_MONITOR_SWITCH, "true");
        System.setProperty(Configs.CONN_RECONNECT_SWITCH, "true");

        int port = ExampleCont.port;

        return new BoltServer(port, true);
    }

    @Override
    public void widgetComponents(WidgetComponents widgetComponents) {
        // 消息队列配置项
        MessageQueueConfigWidget messageQueueConfigWidget = new MessageQueueConfigWidget();
        // 消息队列小部件
        MessageQueueWidget messageQueueWidget = null;
        // 消息队列小部件 - 使用内网的实现 (也可以换成 redis[Redisson， Lettuce], MQ[Apache Pulsar, RocketMQ]等)

        // 消息队列小部件 - 使用实现 内网
        messageQueueWidget = new InternalMessageQueueWidget(messageQueueConfigWidget);

        // 消息队列小部件 - 使用实现 redis[Redisson]
//        messageQueueWidget = new RedissonMessageQueueWidget(messageQueueConfigWidget);

        // 添加发布订阅消息处理类
        messageQueueWidget.addMessageListener(new BroadcastMessageListenerWidget());

        // 添加到部件管理中
        widgetComponents.option(MessageQueueWidget.class, messageQueueWidget);
    }

    @Override
    public void connectionEventProcessor(BoltServer boltServer) {
        GateConnectEventProcessor serverConnectProcessor = new GateConnectEventProcessor();
        GateDisConnectEventProcessor serverDisConnectProcessor = new GateDisConnectEventProcessor();

        serverConnectProcessor.setServer(boltServer);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);
    }

    @Override
    public void registerUserProcessor(BoltServer boltServer) {
        // 处理 - 模块注册
        var registerAsyncUserProcessor = new GateModuleMessageAsyncUserProcessor();
        boltServer.registerUserProcessor(registerAsyncUserProcessor);

        // 处理 - 内部模块转发
        var innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcess();
        boltServer.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        // 处理 - 发布订阅
        WidgetComponents widgetComponents = boltServer.getWidgetComponents();
        var gateBroadcastMessageAsyncUserProcess = new GateBroadcastMessageAsyncUserProcess(widgetComponents);
        boltServer.registerUserProcessor(gateBroadcastMessageAsyncUserProcess);
    }

}
