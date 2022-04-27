package com.iohao.little.game.widget.light.spring.config;


import com.iohao.little.game.widget.light.spring.service.DefaultRedissonDistributedLock;
import com.iohao.little.game.widget.light.spring.service.DistributedLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributedLockAutoConfiguration {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    @ConditionalOnMissingBean
    public DistributedLock distributedLock() {
        return new DefaultRedissonDistributedLock(redissonClient);
    }
}
