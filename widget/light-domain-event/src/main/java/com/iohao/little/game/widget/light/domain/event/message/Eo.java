package com.iohao.little.game.widget.light.domain.event.message;

import com.iohao.little.game.widget.light.domain.event.DomainEventPublish;
import com.iohao.little.game.widget.light.domain.event.disruptor.DomainEventSource;

/**
 * 领域事件的业务接口 (Event Object)
 * <pre>
 *     通常是业务数据载体实现的接口
 *     实现该接口后，会得到领域事件发送的能力
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public interface Eo extends DomainEventSource {
    /**
     * 领域事件发送
     */
    default void send() {
        DomainEventPublish.send(this);
    }
}
