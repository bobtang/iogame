package com.iohao.little.game.widget.light.timer.task.example;

import com.iohao.little.game.widget.light.timer.task.Cache2Kit;
import com.iohao.little.game.widget.light.timer.task.TimerTask;
import com.iohao.little.game.widget.light.timer.task.TimerTaskRegion;
import lombok.Getter;
import org.cache2k.Cache;

/**
 * 定时任务域 - 枚举
 * <pre>
 *     1. 如果枚举类实现 TimerSerializable 接口,
 *     可以将任务序列化和反序列化到数据库中[redis] (实现该接口不是必须的)
 *
 *     2. 必须提供 cache 属性, 用于管理定时任务类
 * </pre>
 *
 * @author 洛朱
 * @date 2021-12-25
 */
public enum TimerTaskEnum implements TimerTaskRegion {
    /**
     * 计时器管理 - 说话
     * <pre>
     *     key is uuid
     *     value is {@link HelloTask}
     * </pre>
     */
    HELLO;

    /** 每个枚举对应一个缓存管理器 */
    @Getter
    private final Cache<String, TimerTask> cache = Cache2Kit.createCache();
}

