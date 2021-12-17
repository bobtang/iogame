package com.iohao.little.game.widget.mq;

import com.iohao.little.game.widget.config.WidgetComponent;

/**
 * 消息队列接口
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public interface MessageQueueWidget extends WidgetComponent {
    default void publish(Object message) {
        this.publish("internal_channel", message);
    }

    void publish(String channel, Object message);

    void addMessageListener(MessageListenerWidget listener);

    void startup();
}
