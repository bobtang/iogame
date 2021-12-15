package com.iohao.little.game.net.kit;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ExecutorKit {
    /**
     * 创建单个线程执行器
     *
     * @param namePrefix 线程名
     * @return 执行器
     */
    public static ExecutorService newSingleThreadExecutor(String namePrefix) {
        ThreadFactory threadFactory = createThreadFactory(namePrefix);
        return newSingleThreadExecutor(threadFactory);
    }

    /**
     * 创建单个线程执行器
     *
     * @param threadFactory 线程创建工厂
     * @return 执行器
     */
    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadExecutor(threadFactory);
    }

    /**
     * 创建线程池
     *
     * @param namePrefix 线程名
     * @return 执行器
     */
    public static ExecutorService newCacheThreadPool(String namePrefix) {
        ThreadFactory threadFactory = createThreadFactory(namePrefix);
        return newCacheThreadPool(threadFactory);
    }

    /**
     * 线程池
     *
     * @param threadFactory 线程创建工厂
     * @return 执行器
     */
    public static ExecutorService newCacheThreadPool(ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    /**
     * 创建固定大小线程执行器
     *
     * @param corePoolSize  容量
     * @param threadFactory 线程工厂
     * @return 执行器
     */
    public static ExecutorService newFixedThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return Executors.newFixedThreadPool(corePoolSize, threadFactory);
    }

    /**
     * 创建固定大小线程执行器
     *
     * @param corePoolSize 容量
     * @param namePrefix   线程名
     * @return 执行器
     */
    public static ExecutorService newFixedThreadPool(int corePoolSize, String namePrefix) {
        ThreadFactory threadFactory = createThreadFactory(namePrefix);
        return newFixedThreadPool(corePoolSize, threadFactory);
    }

    /**
     * 创建单个线程调度执行器
     *
     * @param threadFactory 线程创建工厂
     * @return 调度 执行器
     */
    public static ScheduledExecutorService newSingleScheduled(ThreadFactory threadFactory) {
        return newScheduled(1, threadFactory);
    }

    /**
     * 创建单个线程调度执行器
     *
     * @param namePrefix 线程名
     * @return 调度 执行器
     */
    public static ScheduledExecutorService newSingleScheduled(String namePrefix) {
        ThreadFactory threadFactory = createThreadFactory(namePrefix);
        return newScheduled(1, threadFactory);
    }

    /**
     * 创建指定数量 - 的线程调度执行器
     *
     * @param corePoolSize 容量
     * @param namePrefix   线程名
     * @return 指定数量的 调度 执行器
     */
    public static ScheduledExecutorService newScheduled(int corePoolSize, String namePrefix) {
        ThreadFactory threadFactory = createThreadFactory(namePrefix);
        return newScheduled(corePoolSize, threadFactory);
    }

    /**
     * 创建指定数量 - 的线程调度执行器
     *
     * @param corePoolSize  容量
     * @param threadFactory 线程创建工厂
     * @return 指定数量的 调度 执行器
     */
    public static ScheduledExecutorService newScheduled(int corePoolSize, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }

    /**
     * 创建 线程工厂
     * <pre>
     *     daemon 参数默认是 true
     * </pre>
     *
     * @param namePrefix 线程名
     * @return 线程工厂
     */
    public static ThreadFactory createThreadFactory(String namePrefix) {
        return createThreadFactory(namePrefix, true);
    }

    /**
     * 创建线程工厂
     *
     * @param namePrefix 线程名前缀
     * @param daemon     置是否守护线程
     * @return 线程工厂
     */
    public static ThreadFactory createThreadFactory(String namePrefix, boolean daemon) {
        return ThreadFactoryBuilder.create()
                .setNamePrefix(namePrefix)
                .setDaemon(daemon)
                .build();
    }
}
