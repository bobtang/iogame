package com.iohao.little.game.widget.mq.redis.redisson;

import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.redis.redisson.listener.LionMessageListenerWidget;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RedissonMessageQueueWidgetConsumerTest {

    @Test
    public void startup() throws InterruptedException {
        // 消息消费

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

}