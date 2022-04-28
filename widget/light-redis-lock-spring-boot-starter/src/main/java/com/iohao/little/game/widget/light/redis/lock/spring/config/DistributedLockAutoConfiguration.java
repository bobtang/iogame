package com.iohao.little.game.widget.light.redis.lock.spring.config;


import com.iohao.little.game.widget.light.redis.lock.spring.service.DefaultRedissonDistributedLock;
import com.iohao.little.game.widget.light.redis.lock.spring.service.DistributedLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁 自动装配
 *
 * @author shen
 * @date 2022/3/28
 * @Slogan 慢慢变好，是给自己最好的礼物
 */
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
