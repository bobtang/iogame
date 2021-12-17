package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.redis.redisson.listener.LionMessageListenerWidget;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.config.Config;

@Slf4j
public class RedissonMessageQueueWidgetConsumerTest {

    @Test
    public void startup() throws InterruptedException {
        // 订阅处理类
        LionMessageListenerWidget lionWidgetMessageListener = new LionMessageListenerWidget();

        // 配置项
        var widgetMessageQueueConfig = new MessageQueueConfigWidget();
        MessageQueueWidget messageQueueWidget = new RedissonMessageQueueWidget(widgetMessageQueueConfig);
        // 添加订阅处理
        messageQueueWidget.addMessageListener(lionWidgetMessageListener);

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