package com.iohao.little.game.net.gateway;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.Configs;
import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.BoltServer;
import com.iohao.little.game.net.gateway.common.*;
import com.iohao.little.game.net.gateway.widget.mq.BroadcastMessageListenerWidget;
import com.iohao.little.game.widget.config.WidgetComponents;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.internal.InternalMessageQueueWidget;
import lombok.extern.slf4j.Slf4j;

/**
 * <BR>
 */
@Slf4j
public class GatewayServer {
    BoltServer server;
    int port = ExampleCont.port;

    GateConnectEventProcessor serverConnectProcessor = new GateConnectEventProcessor();
    GateDisConnectEventProcessor serverDisConnectProcessor = new GateDisConnectEventProcessor();

    public void init() {
        System.setProperty(Configs.CONN_MONITOR_SWITCH, "true");
        System.setProperty(Configs.CONN_RECONNECT_SWITCH, "true");

        server = new BoltServer(port, true);
        GateKit.boltServer = server;

        serverConnectProcessor.setServer(server);
        server.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        server.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);

        var processor = new MyServerAsyncUserProcessor();
        server.registerUserProcessor(processor);

        // 处理 - 模块注册
        var registerAsyncUserProcessor = new GateModuleMessageAsyncUserProcessor();
        server.registerUserProcessor(registerAsyncUserProcessor);

        // 处理 - 内部模块转发
        var innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcess();
        server.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        // 扩展的小部件
        WidgetComponents widgetComponents = widget();

        // 处理 - 发布订阅
        var gateBroadcastMessageAsyncUserProcess = new GateBroadcastMessageAsyncUserProcess(widgetComponents);
        server.registerUserProcessor(gateBroadcastMessageAsyncUserProcess);


        log.info("start server ok!");

        /**
         * 启动服务端：先做 netty 配置初始化操作，再做 bind 操作
         * 配置 netty 参数两种方式：[SOFABolt 源码分析11 - Config 配置管理的设计](https://www.jianshu.com/p/76b0be893745)
         */
        server.start();
    }

    private WidgetComponents widget() {

        // 消息队列配置项
        MessageQueueConfigWidget messageQueueConfigWidget = new MessageQueueConfigWidget();
        // 消息队列小部件
        MessageQueueWidget messageQueueWidget = null;
        // 使用内网的实现
        messageQueueWidget = new InternalMessageQueueWidget(messageQueueConfigWidget);

        // 添加发布订阅消息处理类
        messageQueueWidget.addMessageListener(new BroadcastMessageListenerWidget());


        WidgetComponents widgetComponents = new WidgetComponents();
        // 添加到部件管理中
        widgetComponents.option(MessageQueueWidget.class, messageQueueWidget);
        return widgetComponents;
    }

    public static void main(String[] args) {
        // 启动网关
        GatewayServer gatewayServe = new GatewayServer();
        gatewayServe.init();
    }


}
