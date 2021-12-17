package com.iohao.little.game.net.gateway.widget.mq;

import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BroadcastMessageListenerWidget implements MessageListenerWidget<BroadcastMessage> {
    @Override
    public CharSequence channel() {
        return "internal_channel";
    }

    @Override
    public void onMessage(CharSequence channel, BroadcastMessage message) {
        log.info("网关 Broadcast 正在处理 {}", message);
    }
}
