package com.iohao.game.service;

import com.iohao.game.domain.common.lock.ReturnHandle;
import com.iohao.game.domain.common.lock.VoidHandle;

/**
 * redisson分布式锁的适配器
 * 不要参数中的TimeUnit，大部分时候都是TimeUnit.SECOND
 *
 * @author shen
 * @Date 2022/3/28
 * @Slogan  慢慢变好，是给自己最好的礼物
 */
public interface RedissonLockAdpter {

    <M> M tryLockAndExecute(String key, long waitTime, long leaseTime, ReturnHandle<M> action) throws InterruptedException;

    void tryLockAndExecute(String key, long waitTime, long leaseTime, VoidHandle action) throws InterruptedException;

    <M> M lockAndExecute(String key, long leaseTime, ReturnHandle<M> action);

    void lockAndExecute(String key, long leaseTime, VoidHandle action);
}
