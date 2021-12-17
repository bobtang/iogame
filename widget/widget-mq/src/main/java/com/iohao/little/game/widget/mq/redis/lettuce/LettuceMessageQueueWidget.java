package com.iohao.little.game.widget.mq.redis.lettuce;

import com.alibaba.fastjson.JSON;
import com.iohao.little.game.widget.config.WidgetOptions;
import com.iohao.little.game.widget.mq.AbstractMessageQueueWidget;
import com.iohao.little.game.widget.mq.MessageExtWidget;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class LettuceMessageQueueWidget extends AbstractMessageQueueWidget {
    public LettuceMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        super(messageQueueConfigWidget);

        WidgetOptions widgetOptions = messageQueueConfigWidget.getWidgetOptions();

        client = RedisClient.create("redis://localhost");
        connection = client.connectPubSub().sync();
        this.asyncConnection = client.connectPubSub().async();

    }

    RedisPubSubAsyncCommands<String, String> asyncConnection;

    @Override
    public void publish(String channel, Object message) {
        MessageExtWidget messageExtWidget = new MessageExtWidget();
        messageExtWidget.setData(message);
        var jsonString = JSON.toJSONString(messageExtWidget);
        connection.publish(channel, jsonString);
    }

    @Override
    public void addMessageListener(MessageListenerWidget listener) {
        CharSequence channel = listener.channel();

        connection.getStatefulConnection().addListener(new RedisPubSubAdapter<>());

    }

    RedisClient client;
    RedisPubSubCommands<String, String> connection;

    @Override
    public void startup() {
        // TODO: 2021/12/16

    }
}
