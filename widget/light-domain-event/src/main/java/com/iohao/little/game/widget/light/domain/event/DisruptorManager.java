package com.iohao.little.game.widget.light.domain.event;

import com.iohao.little.game.widget.light.domain.event.disruptor.EventDisruptor;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 负责 Disruptor 的管理
 *
 * @author 洛朱
 * @date 2021-12-26
 */
public class DisruptorManager {
    /**
     * 事件主题
     * <pre>
     *     key : topic
     *     value : 事件订阅
     * </pre>
     */
    private final Map<Class<?>, Disruptor<EventDisruptor>> topicMap = new ConcurrentHashMap<>();

    /** 领域事件线程名前缀 */
    @Setter
    @Getter
    private String threadNamePrefix = "iohao.domain";

    /**
     * 获取所有Disruptor
     *
     * @return Disruptor集合
     */
    Collection<Disruptor<EventDisruptor>> listDisruptor() {
        return topicMap.values();
    }

    void forEach(Consumer<Disruptor<EventDisruptor>> action) {
        listDisruptor().parallelStream().forEach(action);
    }

    /**
     * 获取领域消息主题对应的Disruptor
     *
     * @param topic 领域消息主题
     * @return Disruptor
     */
    Disruptor<EventDisruptor> getDisruptor(final Class<?> topic) {
        return topicMap.get(topic);
    }

    void put(Class<?> topic, Disruptor<EventDisruptor> disruptor) {
        topicMap.putIfAbsent(topic, disruptor);
    }

    private DisruptorManager() {
    }

    public static DisruptorManager me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final DisruptorManager ME = new DisruptorManager();
    }

}
