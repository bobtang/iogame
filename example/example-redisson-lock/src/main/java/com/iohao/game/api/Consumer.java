package com.iohao.game.api;

import cn.hutool.extra.spring.SpringUtil;
import com.iohao.game.domain.entity.UserWallet;
import com.iohao.game.service.DistributedLock;
import com.iohao.game.service.RedissonLockAdptee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class Consumer {

    @Resource
    @Qualifier("DefaultRedissonDistributedLock")
    private DistributedLock distributedLock;

    @Async
    public void consume(UserWallet wallet) {
        RedissonLockAdptee redissonLockAdptee = SpringUtil.getBean(RedissonLockAdptee.class);
        try {
            redissonLockAdptee.tryLockAndExecute(wallet.getUserId(), 10, 1, () -> {
                System.out.println("线程：" + Thread.currentThread().getName() + "拿到锁了");
                BigDecimal sub = new BigDecimal(1d);
                wallet.setBalance(wallet.getBalance().subtract(sub));
                System.out.println("【" + wallet.getName() + "】当前余额【" + wallet.getBalance() + "】");
            });
        } catch (InterruptedException e) {
            System.out.println("获取锁等待失败");
        }
    }

    @Async
    public void consumeWaitTimeout(UserWallet wallet, long waitTime, long leaseTime) {
        RedissonLockAdptee redissonLockAdptee = SpringUtil.getBean(RedissonLockAdptee.class);
        try {
            redissonLockAdptee.tryLockAndExecute(wallet.getUserId(), waitTime, leaseTime, () -> {
                System.out.println("线程：" + Thread.currentThread().getName() + "拿到锁了");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BigDecimal sub = new BigDecimal(1d);
                wallet.setBalance(wallet.getBalance().subtract(sub));
                System.out.println("【" + wallet.getName() + "】当前余额【" + wallet.getBalance() + "】");
            });
        } catch (InterruptedException e) {
            System.out.println("获取锁等待失败");
        }
    }
}
