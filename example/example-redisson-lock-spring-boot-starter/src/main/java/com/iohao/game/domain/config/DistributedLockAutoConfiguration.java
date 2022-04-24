package com.iohao.game.domain.config;

import com.iohao.game.service.DefaultRedissonDistributedLock;
import com.iohao.game.service.DistributedLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributedLockAutoConfiguration {

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Bean
    public DistributedLock distributedLock() {
        return new DefaultRedissonDistributedLock(redissonClient);
    }
}
