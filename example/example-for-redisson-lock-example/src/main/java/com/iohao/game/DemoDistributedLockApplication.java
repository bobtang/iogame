package com.iohao.game;

@SpringBootApplication
//开启线程池
@EnableAsync
public class DemoDistributedLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoDistributedLockApplication.class, args);
    }
}
