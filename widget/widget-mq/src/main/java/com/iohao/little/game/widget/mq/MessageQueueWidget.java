package com.iohao.little.game.widget.mq;

import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.config.WidgetComponent;

/**
 * 消息队列部件
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public interface MessageQueueWidget extends WidgetComponent {
    default void publish(BroadcastMessage message) {
        this.publish("internal_channel", message);
    }

    /**
     * 发布消息
     *
     * @param channel (mq 中的 topic; redis 中的 channel)
     * @param message message
     */
    void publish(String channel, BroadcastMessage message);

    /**
     * 添加监听者（消息订阅者）
     *
     * @param listener 监听者
     */
    void addMessageListener(MessageListenerWidget listener);

    /**
     * 获取
     *
     * @param channel
     * @return
     */
    default MessageListenerWidget getByChannel(String channel) {
        return null;
    }

    /**
     * 启动
     */
    void startup();
}
