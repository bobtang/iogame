package com.iohao.game.service;

import com.iohao.game.domain.common.lock.ReturnHandle;
import com.iohao.game.domain.common.lock.VoidHandle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockAdptee implements DistributedLock,RedissonLockAdpter {

    @Resource
    private DefaultRedissonDistributedLock defaultRedissonDistributedLock;


    @Override
    public <M> M tryLockAndExecute(String key, long waitTime, long leaseTime, TimeUnit unit, ReturnHandle<M> action) throws InterruptedException {
        return defaultRedissonDistributedLock.tryLockAndExecute(key, waitTime, leaseTime, unit, action);
    }

    @Override
    public <M> void tryLockAndExecute(String key, long waitTime, long leaseTime, TimeUnit unit, VoidHandle action) throws InterruptedException {
        defaultRedissonDistributedLock.tryLockAndExecute(key, waitTime, leaseTime, unit, action);
    }

    @Override
    public <M> M lockAndExecute(String key, long leaseTime, TimeUnit unit, ReturnHandle<M> action) {
        return defaultRedissonDistributedLock.lockAndExecute(key, leaseTime, unit, action);
    }

    @Override
    public void lockAndExecute(String key, long leaseTime, TimeUnit unit, VoidHandle action) {
        defaultRedissonDistributedLock.lockAndExecute(key, leaseTime, unit, action);
    }

    @Override
    public boolean unlock(String key) {
        return defaultRedissonDistributedLock.unlock(key);
    }

    @Override
    public <M> M tryLockAndExecute(String key, long waitTime, long leaseTime, ReturnHandle<M> action) throws InterruptedException {
        return defaultRedissonDistributedLock.tryLockAndExecute(key, waitTime, leaseTime, TimeUnit.SECONDS, action);
    }

    @Override
    public void tryLockAndExecute(String key, long waitTime, long leaseTime, VoidHandle action) throws InterruptedException {
        defaultRedissonDistributedLock.tryLockAndExecute(key, waitTime, leaseTime, TimeUnit.SECONDS, action);
    }

    @Override
    public <M> M lockAndExecute(String key, long leaseTime, ReturnHandle<M> action) {
        return defaultRedissonDistributedLock.lockAndExecute(key, leaseTime, TimeUnit.SECONDS, action);
    }

    @Override
    public void lockAndExecute(String key, long leaseTime, VoidHandle action) {
        defaultRedissonDistributedLock.lockAndExecute(key, leaseTime, TimeUnit.SECONDS, action);
    }
}
