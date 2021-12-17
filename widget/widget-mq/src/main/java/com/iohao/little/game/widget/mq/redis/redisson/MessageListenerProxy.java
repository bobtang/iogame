package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.MessageExtWidget;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import org.redisson.api.listener.MessageListener;

/**
 * 数据适配
 * <pre>
 * </pre>
 * @author 洛朱
 * @date 2021/12/17
 */
public class MessageListenerProxy implements MessageListener<MessageExtWidget> {
    MessageListenerWidget< Object> messageListenerWidget;

    public MessageListenerProxy(MessageListenerWidget<Object> listener) {
        this.messageListenerWidget = listener;
    }

    @Override
    public void onMessage(CharSequence channel, MessageExtWidget msg) {
       var data = msg.getData();
        messageListenerWidget.onMessage(channel,data);
    }
}
