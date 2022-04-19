//package com.iohao.game.domain.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * redisson的基本配置
// *
// * @author shen
// * @Date 2022/3/28
// * @Slogan  慢慢变好，是给自己最好的礼物
// */
//public class RedissonConfig {
//
//    private volatile static RedissonClient redissonClient;
//
//    public static RedissonClient me() {
//        if (Objects.isNull(redissonClient)) {
//            return getRedissonClientFromConfig();
//        }
//        return redissonClient;
//    }
//
//    public static RedissonClient getRedissonClientFromConfig() {
//        Config config = null;
//        try {
//            config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (Objects.isNull(config)) {
//            config.useSingleServer()
//                    .setAddress("redis://localhost:6379");
//        }
//        RedissonClient redisson = Redisson.create(config);
//        return redisson;
//    }
//}
