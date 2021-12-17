package com.iohao.little.game.widget.mq.internal;

import com.iohao.little.game.widget.mq.MessageQueueWidget;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;
import com.iohao.little.game.widget.mq.dto.Lion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class InternalMessageQueueWidgetProducerTest {

    @Test
    public void startup() throws InterruptedException {
        var widgetMessageQueueConfig = new MessageQueueConfigWidget();
        MessageQueueWidget messageQueueWidget = new InternalMessageQueueWidget(widgetMessageQueueConfig);
        log.info("MessageQueueProducer - start ok!");
        int i = 0;
        while (true) {
            Lion lion = new Lion();
            lion.setId(i++);
            lion.setName("name is : " + i);

            String channel = "lionChannel";
            messageQueueWidget.publish(channel, lion);
            Thread.sleep(2000);
        }
    }
}