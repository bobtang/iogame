package com.iohao.little.game.widget.light;

import com.iohao.little.game.widget.light.api.ExecutorsDistributeLock;
import junit.framework.TestCase;

public class DefaultRedissonDistributedLockTest extends TestCase {

    public void test() {
        ExecutorsDistributeLock lock = new ExecutorsDistributeLock();
//        lock.testRlock();
        lock.testlockWithTime();

        //沉睡一下，不然会结束进程
        try {
            Thread.sleep(200000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}