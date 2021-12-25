package com.iohao.little.game.widget.light.timer.task;

/**
 * 定时任务序列化接口
 *
 * @author 洛朱
 * @date 2021-12-25
 * @see Cache2Kit#setStoreSetting(Cache2Kit.StoreSetting)
 */
public interface TimerTaskSerializable extends TimerTaskRegion {
    /**
     * 序列化到 redis 中
     */
    default void serialize() {
        Cache2Kit.serialize(this.name(), this.getCache());
    }

    /**
     * 反序列化
     */
    default void deserialize() {
        Cache2Kit.deserialize(this.name(), this.getCache());
    }
}
