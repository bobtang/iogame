package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.WidgetMessageQueue;
import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;
import com.iohao.little.game.widget.mq.redis.redisson.listener.LionWidgetMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.config.Config;

@Slf4j
public class RedissonWidgetMessageQueueConsumerTest {

    @Test
    public void startup() throws InterruptedException {
        var widgetMessageQueueConfig = new WidgetMessageQueueConfig();
        WidgetMessageQueue widgetMessageQueue = new RedissonWidgetMessageQueue(widgetMessageQueueConfig);

        LionWidgetMessageListener lionWidgetMessageListener = new LionWidgetMessageListener();

        // sub
        widgetMessageQueue.addMessageListener(lionWidgetMessageListener);
        log.info("MessageQueueConsumer - start ok!");
        Thread.sleep(1000 * 100);
    }

    @Test
    public void name() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        var redisson = Redisson.create(config);
    }
}