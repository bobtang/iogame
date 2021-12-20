package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import org.redisson.api.listener.MessageListener;

/**
 * 数据适配
 * <pre>
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
public class BroadcastMessageListenerProxy implements MessageListener<BroadcastMessage> {
    MessageListenerWidget<Object> messageListenerWidget;

    public BroadcastMessageListenerProxy(MessageListenerWidget<Object> listener) {
        this.messageListenerWidget = listener;
    }

    @Override
    public void onMessage(CharSequence channel, BroadcastMessage broadcastMessage) {
        ResponseMessage responseMessage = broadcastMessage.getResponseMessage();
        messageListenerWidget.onMessage(responseMessage, channel, broadcastMessage);
    }
}
