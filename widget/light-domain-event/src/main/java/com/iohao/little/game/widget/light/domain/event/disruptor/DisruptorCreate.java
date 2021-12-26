package com.iohao.little.game.widget.light.domain.event.disruptor;

import com.iohao.little.game.widget.light.domain.event.DomainEventContextParam;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 领域事件构建接口
 * <pre>
 *     创建disruptor
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public interface DisruptorCreate {
    /**
     * 根据topic（领域消息主题）创建disruptor
     *
     * @param topic 主题
     * @param param param
     * @return Disruptor
     */
    Disruptor<EventDisruptor> createDisruptor(Class<?> topic, DomainEventContextParam param);
}
