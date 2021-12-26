package com.iohao.little.game.widget.light.domain.event.disruptor;

import com.iohao.little.game.widget.light.domain.event.message.Topic;

/**
 * 领域事件接口 - 源事件源
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public interface DomainEventSource extends Topic {
    /**
     * 获取领域事件主题
     *
     * @return 领域事件主题
     */
    @Override
    default Class<?> getTopic() {
        return this.getClass();
    }

    /**
     * 获取事件源
     *
     * @param <T> source
     * @return 事件源
     */
    @SuppressWarnings("unchecked")
    default <T> T getSource() {
        return (T) this;
    }
}
