package com.iohao.little.game.widget.mq.internal;

import com.iohao.little.game.widget.mq.AbstractWidgetMessageQueue;
import com.iohao.little.game.widget.mq.WidgetMessageListener;
import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;

public class SimpleWidgetMessageQueue extends AbstractWidgetMessageQueue {

    public SimpleWidgetMessageQueue(WidgetMessageQueueConfig widgetMessageQueueConfig) {
        super(widgetMessageQueueConfig);
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
