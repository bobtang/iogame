package com.iohao.little.game.widget.broadcast.internal;

import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GateBroadcastWidgetComponents {
    public static void configBroadcastWidgetComponents(WidgetComponents widgetComponents) {
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
}
