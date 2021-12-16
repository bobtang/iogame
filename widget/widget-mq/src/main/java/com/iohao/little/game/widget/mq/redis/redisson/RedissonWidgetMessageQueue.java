package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.config.WidgetOptions;
import com.iohao.little.game.widget.mq.AbstractWidgetMessageQueue;
import com.iohao.little.game.widget.mq.WidgetMessageListener;
import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;

public class RedissonWidgetMessageQueue extends AbstractWidgetMessageQueue {
    public RedissonWidgetMessageQueue(WidgetMessageQueueConfig widgetMessageQueueConfig) {
        super(widgetMessageQueueConfig);

        WidgetOptions widgetOptions = widgetMessageQueueConfig.getWidgetOptions();



    }

    @Override
    public void publish(String channel, Object message) {

    }

    @Override
    public void addMessageListener(WidgetMessageListener listener) {

    }

    @Override
    public void startup() {

    }
}
