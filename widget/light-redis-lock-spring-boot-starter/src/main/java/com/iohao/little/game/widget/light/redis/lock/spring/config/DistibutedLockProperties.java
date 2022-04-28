package com.iohao.little.game.widget.light.redis.lock.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "iogame")
public class DistibutedLockProperties {
    private String redissonConfigName;

    public String getRedissonConfigName() {
        return redissonConfigName;
    }

    public void setRedissonConfigName(String redissonConfigName) {
        this.redissonConfigName = redissonConfigName;
    }
}
