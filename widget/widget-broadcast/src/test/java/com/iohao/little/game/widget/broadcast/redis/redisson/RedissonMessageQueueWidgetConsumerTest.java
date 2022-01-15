package com.iohao.little.game.widget.broadcast.redis.redisson;

import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.broadcast.redis.redisson.listener.LionMessageListenerWidgetTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RedissonMessageQueueWidgetConsumerTest {

    @Test
    public void startup() throws InterruptedException {
        // 消息消费

        // 订阅处理类
        LionMessageListenerWidgetTest lionWidgetMessageListener = new LionMessageListenerWidgetTest();

        // 配置项
        var widgetMessageQueueConfig = new MessageQueueConfigWidget();
        MessageQueueWidget messageQueueWidget = new RedissonMessageQueueWidget(widgetMessageQueueConfig);
        // 添加订阅处理
        messageQueueWidget.addMessageListener(lionWidgetMessageListener);

        log.info("MessageQueueConsumer - start ok!");
        Thread.sleep(1000 * 100);
    }

}