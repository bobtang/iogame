package com.iohao.little.game.widget.broadcast;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMessageQueueWidget implements MessageQueueWidget {
    protected MessageQueueConfigWidget messageQueueConfigWidget;
    @Getter
    protected ConcurrentHashMap<CharSequence, MessageListenerWidget> listenerWidgetMap = new ConcurrentHashMap<>();

    public AbstractMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        this.messageQueueConfigWidget = messageQueueConfigWidget;
    }
}
