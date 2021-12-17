package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.WidgetMessageExt;
import com.iohao.little.game.widget.mq.WidgetMessageListener;
import org.redisson.api.listener.MessageListener;

public class MessageListenerProxy implements MessageListener<WidgetMessageExt> {
    WidgetMessageListener< Object> widgetMessageListener;

    public MessageListenerProxy(WidgetMessageListener<Object> listener) {
        this.widgetMessageListener = listener;
    }

    @Override
    public void onMessage(CharSequence channel, WidgetMessageExt msg) {
       var data = msg.getData();
        widgetMessageListener.onMessage(channel,data);
    }
}
