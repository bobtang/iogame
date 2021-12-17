package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.WidgetMessageQueue;
import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;
import com.iohao.little.game.widget.mq.dto.Lion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RedissonWidgetMessageQueueProducerTest {

    @Test
    public void startup() throws InterruptedException {
        var widgetMessageQueueConfig = new WidgetMessageQueueConfig();
        WidgetMessageQueue widgetMessageQueue = new RedissonWidgetMessageQueue(widgetMessageQueueConfig);
        log.info("MessageQueueProducer - start ok!");
        int i = 0;
        while (true) {
            Lion lion = new Lion();
            lion.setId(i++);
            lion.setName("name is : " + i);

            String channel = "lionChannel";
            widgetMessageQueue.publish(channel, lion);
            Thread.sleep(2000);
        }
    }
}