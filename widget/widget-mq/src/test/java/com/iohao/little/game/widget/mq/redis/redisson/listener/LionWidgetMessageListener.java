package com.iohao.little.game.widget.mq.redis.redisson.listener;

import com.iohao.little.game.widget.mq.WidgetMessageListener;
import com.iohao.little.game.widget.mq.dto.Lion;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LionWidgetMessageListener implements WidgetMessageListener<Lion> {
    @Override
    public CharSequence channel() {
        return "lionChannel";
    }

    @Override
    public void onMessage(CharSequence channel, Lion message) {
        log.info("channel: {} - message: {}", channel, message);
    }
}
