package com.iohao.example.client;

import com.iohao.little.game.widget.config.WidgetComponents;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.internal.InternalMessageQueueWidget;

public class ClientWidgetComponentsInit {
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
}
