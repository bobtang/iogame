package com.iohao.game;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class TestLock {

    @Resource
    private Consumer consumer;

    public void testLock() {
        UserWallet userWallet = new UserWallet();
        userWallet.setUserId("10086");
        userWallet.setBalance(new BigDecimal(1000000L));
        userWallet.setName("钱百万");

        for (int i = 0; i < 100; i++) {
            consumer.consume(userWallet);
        }
    }
}
