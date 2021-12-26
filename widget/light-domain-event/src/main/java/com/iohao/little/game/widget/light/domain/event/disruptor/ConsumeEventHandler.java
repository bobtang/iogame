package com.iohao.little.game.widget.light.domain.event.disruptor;

import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;
import com.lmax.disruptor.EventHandler;

/**
 * @author 洛朱
 * @date 2021-12-26
 */
public record ConsumeEventHandler(
        DomainEventHandler<?> eventHandler) implements EventHandler<EventDisruptor> {

    @Override
    public void onEvent(EventDisruptor event, long sequence, boolean endOfBatch) {
        if (event.isEventSource()) {
            eventHandler.onEvent(event.getDomainEventSource(), sequence, endOfBatch);
        } else {
            eventHandler.onEvent(event.getValue(), sequence, endOfBatch);
        }
    }
}
