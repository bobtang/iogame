package com.iohao.little.game.widget.light.domain.event.message;

/**
 * 领域事件消费接口, 接收一个领域事件
 *
 * @param <T> T 领域实体
 * @author 洛朱
 * @date 2021-12-26
 */
@FunctionalInterface
public interface DomainEventHandler<T> {

    /**
     * 事件处理
     *
     * @param event      领域实体
     * @param endOfBatch endOfBatch
     */
    void onEvent(final T event, final boolean endOfBatch);

    /**
     * 事件处理
     *
     * @param event      领域实体
     * @param sequence   sequence
     * @param endOfBatch endOfBatch
     */
    default void onEvent(final T event, final long sequence, final boolean endOfBatch) {
        this.onEvent(event, endOfBatch);
    }

    /**
     * 获取领域事件名
     *
     * @return 领域事件名
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
