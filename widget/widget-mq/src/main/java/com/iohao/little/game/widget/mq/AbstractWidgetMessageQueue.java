package com.iohao.little.game.widget.mq;

public abstract class AbstractWidgetMessageQueue implements WidgetMessageQueue {
    WidgetMessageQueueConfig widgetMessageQueueConfig;

    public AbstractWidgetMessageQueue(WidgetMessageQueueConfig widgetMessageQueueConfig) {
        this.widgetMessageQueueConfig = widgetMessageQueueConfig;
    }
}
