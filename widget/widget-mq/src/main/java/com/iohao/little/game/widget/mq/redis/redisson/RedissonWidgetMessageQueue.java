package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.config.WidgetOptions;
import com.iohao.little.game.widget.mq.AbstractWidgetMessageQueue;
import com.iohao.little.game.widget.mq.WidgetMessageExt;
import com.iohao.little.game.widget.mq.WidgetMessageListener;
import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Slf4j
public class RedissonWidgetMessageQueue extends AbstractWidgetMessageQueue {


    RedissonClient redisson;

    public RedissonWidgetMessageQueue(WidgetMessageQueueConfig widgetMessageQueueConfig) {
        super(widgetMessageQueueConfig);
        WidgetOptions widgetOptions = widgetMessageQueueConfig.getWidgetOptions();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        this.redisson = Redisson.create(config);
    }

    @Override
    public void publish(String channel, Object message) {
        System.out.println("publish");
        log.info("publish: {} - {}", channel, message);
        var widgetMessageExt = new WidgetMessageExt();
        widgetMessageExt.setData(message);

        var topic = redisson.getTopic(channel);
        topic.publish(widgetMessageExt);
    }

    @Override
    public void addMessageListener(WidgetMessageListener listener) {
        CharSequence channel = listener.channel();
        RTopic topic = redisson.getTopic(channel.toString());
        MessageListenerProxy messageListenerProxy = new MessageListenerProxy(listener);
        topic.addListener(WidgetMessageExt.class, messageListenerProxy);
    }


    @Override
    public void startup() {
        // TODO: 2021/12/16

    }
}
