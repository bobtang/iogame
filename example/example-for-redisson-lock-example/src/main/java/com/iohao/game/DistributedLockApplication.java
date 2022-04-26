package com.iohao.game;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DistributedLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);

        TestLock lock = SpringUtil.getBean(TestLock.class);
        lock.testLock();
    }
}
