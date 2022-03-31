package com.iohao.game;

import cn.hutool.extra.spring.SpringUtil;
import com.iohao.game.domain.common.lock.ReturnHandle;
import com.iohao.game.domain.entity.UserWallet;
import com.iohao.game.service.DistributedLock;
import com.iohao.game.service.RedissonLockAdptee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer {

    @Resource
    @Qualifier("DefaultRedissonDistributedLock")
    private DistributedLock distributedLock;

    public void consume(UserWallet wallet) {


        System.out.println("当前线程：" + Thread.currentThread().getName() + "，正在扣费中");
        try {
            distributedLock.tryLockAndExecute(wallet.getUserId(), 10, 1, TimeUnit.SECONDS, () -> {
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
    public void consumeWithAsyncAnnotation(UserWallet wallet) {
        RedissonLockAdptee redissonLockAdptee = SpringUtil.getBean(RedissonLockAdptee.class);

        System.out.println("当前线程：" + Thread.currentThread().getName() + "，正在扣费中");
        try {
            redissonLockAdptee.tryLockAndExecute(wallet.getUserId(), 10, 1, () -> {
                System.out.println("线程：" + Thread.currentThread().getName() + "拿到锁了");
                return;
                //            BigDecimal sub = new BigDecimal(1d);
                //            wallet.setBalance(wallet.getBalance().subtract(sub));
                //            System.out.println("【" + wallet.getName() + "】当前余额【" + wallet.getBalance() + "】");
            });
        } catch (InterruptedException e) {
            System.out.println("获取锁等待失败");
        }
    }

    @Async
    public void consumeTry(UserWallet wallet) {
        RedissonLockAdptee redissonLockAdptee = SpringUtil.getBean(RedissonLockAdptee.class);
        ReturnHandle<String> returnHandle = () -> {
            System.out.println("拿到锁了");
            return Thread.currentThread().getName();
        };

        System.out.println("当前线程：" + Thread.currentThread().getName() + "，正在扣费中");
        try {
            String s = redissonLockAdptee.tryLockAndExecute(wallet.getUserId(), 10, 1, returnHandle);
            System.out.println(s);
        } catch (InterruptedException e) {
            System.out.println("获取锁等待失败");
        }
    }

}
