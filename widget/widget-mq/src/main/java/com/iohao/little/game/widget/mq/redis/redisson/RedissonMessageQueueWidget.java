package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.config.WidgetOptions;
import com.iohao.little.game.widget.mq.AbstractMessageQueueWidget;
import com.iohao.little.game.widget.mq.MessageExtWidget;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Slf4j
public class RedissonMessageQueueWidget extends AbstractMessageQueueWidget {


    RedissonClient redisson;

    public RedissonMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        super(messageQueueConfigWidget);
        WidgetOptions widgetOptions = messageQueueConfigWidget.getWidgetOptions();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        this.redisson = Redisson.create(config);
    }

    @Override
    public void publish(String channel, Object message) {
        System.out.println("publish");
        log.info("publish: {} - {}", channel, message);
        var widgetMessageExt = new MessageExtWidget();
        widgetMessageExt.setData(message);

        var topic = redisson.getTopic(channel);
        topic.publish(widgetMessageExt);
    }

    @Override
    public void addMessageListener(MessageListenerWidget listener) {
        CharSequence channel = listener.channel();
        RTopic topic = redisson.getTopic(channel.toString());
        // 数据适配
        MessageListenerProxy messageListenerProxy = new MessageListenerProxy(listener);
        topic.addListener(MessageExtWidget.class, messageListenerProxy);
    }


    @Override
    public void startup() {
        // TODO: 2021/12/16

    }
}
