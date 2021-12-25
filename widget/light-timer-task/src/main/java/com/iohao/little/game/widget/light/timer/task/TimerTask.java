package com.iohao.little.game.widget.light.timer.task;

import org.cache2k.expiry.ValueWithExpiryTime;

import java.io.Serializable;

/**
 * 定时任务
 *
 * @author 洛朱
 * @date 2021-12-25
 */
public interface TimerTask extends ValueWithExpiryTime, Serializable {
    /**
     * 执行方法
     * <pre>
     *     时间到就执行, 否则就不执行
     * </pre>
     */
    void execute();

    /**
     * 启动定时器任务
     *
     * @return me
     */
    <T extends TimerTask> T task();

    /**
     * 取消定时任务
     */
    void cancel();

    /**
     * 暂停任务一段时间
     * <pre>
     *     连续调用暂停时间不会累加, 需要到任务加入到队列中
     * </pre>
     *
     * @param stopTimeMillis 暂停的时间
     */
    void pause(long stopTimeMillis);

}
