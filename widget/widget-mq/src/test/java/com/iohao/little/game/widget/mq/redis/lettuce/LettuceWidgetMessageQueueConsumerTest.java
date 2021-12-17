package com.iohao.little.game.widget.mq.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LettuceWidgetMessageQueueConsumerTest {


    @Test
    public void name() throws InterruptedException {
        log.info("hello");
        System.out.println("~~~");
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands<String, String> sync = connection.sync();

        sync.set("key", "abc");
        Thread.sleep(10);
        String value = (String) sync.get("key");


        log.info("value: {}", value);

    }
}