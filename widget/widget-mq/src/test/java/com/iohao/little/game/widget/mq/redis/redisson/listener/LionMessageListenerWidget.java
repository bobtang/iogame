package com.iohao.little.game.widget.mq.redis.redisson.listener;

import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import com.iohao.little.game.widget.mq.dto.Lion;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LionMessageListenerWidget implements MessageListenerWidget<Lion> {
    @Override
    public CharSequence channel() {
        return "internal_channel";
    }

    @Override
    public void onMessage(ResponseMessage responseMessage, CharSequence channel, BroadcastMessage broadcastMessage) {
        log.info("channel: {} - message: {}", channel, responseMessage);
    }
}
