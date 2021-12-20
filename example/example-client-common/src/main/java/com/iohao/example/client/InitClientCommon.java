package com.iohao.example.client;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.BoltClientServer;
import com.iohao.little.game.net.BoltClientServerSetting;
import com.iohao.little.game.net.common.ClientConnectEventProcessor;
import com.iohao.little.game.net.common.ClientDisConnectEventProcessor;
import com.iohao.little.game.net.common.ClientRequestMessageAsyncUserProcessor;
import com.iohao.little.game.net.core.RemoteAddress;
import com.iohao.little.game.widget.config.WidgetComponents;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.internal.InternalMessageQueueWidget;

/**
 * 示例， 抽出一些公共的 clientServer 配置代码
 */
public class InitClientCommon {
    public RemoteAddress createRemoteAddress() {
        String ip = ExampleCont.ip;
        int port = ExampleCont.port;
        return new RemoteAddress(ip, port);
    }

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

        // TODO: 2021/12/19 注意 发布订阅的内核 需要逻辑服与网关一致

        // 添加到部件管理中
        widgetComponents.option(MessageQueueWidget.class, messageQueueWidget);
    }

    public void registerUserProcessor(BoltClientServer boltClientServer) {
        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();

        ClientRequestMessageAsyncUserProcessor processor = new ClientRequestMessageAsyncUserProcessor();
        processor.setBoltClientServerSetting(boltClientServerSetting);

        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.registerUserProcessor(processor);
    }

    public void connectionEventProcessor(BoltClientServer boltClientServer) {

        ClientConnectEventProcessor clientConnectEventProcessor = new ClientConnectEventProcessor();
        ClientDisConnectEventProcessor clientDisConnectProcessor = new ClientDisConnectEventProcessor();

        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();
        var moduleKey = boltClientServerSetting.getModuleKey();
        clientConnectEventProcessor.setModuleKey(moduleKey);

        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectEventProcessor);
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);
    }

    public static InitClientCommon me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final InitClientCommon ME = new InitClientCommon();
    }
}
