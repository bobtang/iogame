package com.iohao.little.game.widget.mq;

import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.message.common.BroadcastMessage;

/**
 * 消息监听者，消息处理
 *
 * @param <T> t 先做个预留，暂时没什么鸟用
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
     * @param responseMessage  responseMessage
     * @param channel          channel
     * @param broadcastMessage broadcastMessage
     */
    void onMessage(ResponseMessage responseMessage, CharSequence channel, BroadcastMessage broadcastMessage);
}
