package com.iohao.little.game.net.gateway.widget.mq;

import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.mq.MessageListenerWidget;

public class BroadcastMessageListenerWidget implements MessageListenerWidget<BroadcastMessage> {
    @Override
    public CharSequence channel() {
        return null;
    }

    @Override
    public void onMessage(CharSequence channel, BroadcastMessage message) {

    }
}
