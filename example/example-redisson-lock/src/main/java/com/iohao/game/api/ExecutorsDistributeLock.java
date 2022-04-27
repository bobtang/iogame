//package com.iohao.game.api;
//
//import com.iohao.game.domain.entity.UserWallet;
//import com.iohao.game.service.DefaultRedissonDistributedLock;
//import com.iohao.game.service.DistributedLock;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * 使用自建线程池模拟多线程环境竞争锁
// *
// * @author shen
// * @date 2022/3/31
// * @Slogan 慢慢变好，是给自己最好的礼物
// */
//@Component
//public class ExecutorsDistributeLock {
//
//    public final DistributedLock distributedLock = new DefaultRedissonDistributedLock();
//
//    public static ExecutorService executors = new ThreadPoolExecutor(
//            2,
//            8,
//            0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<>(100)
//    );
//
//    public void testRlock() {
//        UserWallet wallet = new UserWallet();
//        wallet.setUserId("10086");
//        wallet.setName("中国移动");
//        wallet.setBalance(BigDecimal.valueOf(100000L));
//        for (int i = 0; i < 100; i++) {
//            executors.submit(() -> process(wallet));
//        }
//    }
//
//    private void process(UserWallet wallet) {
//        //执行时间
//        long leaseTime = -1L;
//        distributedLock.lockAndExecute(wallet.getUserId(), leaseTime, TimeUnit.SECONDS, () -> {
//            System.out.println("线程：" + Thread.currentThread().getName() + "拿到锁了");
//            BigDecimal sub = new BigDecimal(1d);
//            wallet.setBalance(wallet.getBalance().subtract(sub));
//            System.out.println("【" + wallet.getName() + "】当前余额【" + wallet.getBalance() + "】");
//        });
//    }
//}
