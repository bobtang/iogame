package com.iohao.little.game.widget.broadcast.internal;

import com.iohao.little.game.widget.broadcast.AbstractMessageQueueWidget;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.widget.broadcast.MessageListenerWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GateInternalMessageQueueWidget extends AbstractMessageQueueWidget {

    public GateInternalMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        super(messageQueueConfigWidget);
    }

    @Override
    public void publish(String channel, BroadcastMessage message) {

    }

    @Override
    public void addMessageListener(MessageListenerWidget listener) {
        CharSequence channel = listener.channel();

        listenerWidgetMap.put(channel, listener);
    }

    @Override
    public MessageListenerWidget getByChannel(String channel) {
        return listenerWidgetMap.get(channel);
    }

    @Override
    public void startup() {

    }
}
