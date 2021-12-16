package com.iohao.little.game.widget.mq;

public interface WidgetMessageQueue {
    void publish(String channel, Object message);

    void addMessageListener(WidgetMessageListener listener);

    void startup();
}
