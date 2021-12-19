package com.iohao.little.game.widget.mq;

/**
 * 消息监听者，消息处理
 *
 * @author 洛朱
 * @date 2021/12/16
 */
public interface MessageListenerWidget<T> {
    /**
     * 监听的频道名 channel
     * <pre>
     *     mq 中的 topic
     *     redis 中的 channel
     * </pre>
     *
     * @return 监听的频道名
     */
    CharSequence channel();

    /**
     * 消息处理
     * <pre>
     *     当消息发布者发布消息后，监听者（订阅者）会进行处理
     * </pre>
     *
     * @param channel channel
     * @param message message
     */
    void onMessage(CharSequence channel, T message);
}
