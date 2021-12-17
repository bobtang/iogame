package com.iohao.little.game.widget.mq.redis.lettuce;

import com.alibaba.fastjson.JSON;
import com.iohao.little.game.widget.mq.dto.Lion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LettuceMessageQueueWidgetConsumerTest {


    @Test
    public void name() throws InterruptedException {
        log.info("hello");
//        System.out.println("~~~");
//        RedisClient client = RedisClient.create("redis://localhost");
//        StatefulRedisConnection<String, String> connection = client.connect();
//        RedisStringCommands<String, String> sync = connection.sync();
//
//        sync.set("key", "abc1");
//        Thread.sleep(10);
//        String value = (String) sync.get("key");
//
//
//        log.info("value: {}", value);

        Lion lion = new Lion();
        lion.setId(100);
        lion.setName("雷少");

        String jsonString = JSON.toJSONString(lion);
        log.info("{}", jsonString);

        Lion jsonLion = null;
    }
}