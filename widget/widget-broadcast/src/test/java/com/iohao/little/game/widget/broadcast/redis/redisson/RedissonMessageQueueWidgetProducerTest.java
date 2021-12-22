package com.iohao.little.game.widget.broadcast.redis.redisson;

import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.broadcast.dto.Lion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RedissonMessageQueueWidgetProducerTest {

    @Test
    public void startup() throws InterruptedException {
        var widgetMessageQueueConfig = new MessageQueueConfigWidget();
        MessageQueueWidget messageQueueWidget = new RedissonMessageQueueWidget(widgetMessageQueueConfig);
        log.info("MessageQueueProducer - start ok!");
        int i = 0;
        String channel = "internal_channel";
        while (true) {

            Lion lion = new Lion();
            lion.setId(i++);
            lion.setName("name is : " + i);

            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setData(lion);

            BroadcastMessage broadcastMessage = new BroadcastMessage();
            broadcastMessage.setChannel(channel);
            broadcastMessage.setResponseMessage(responseMessage);

            // 消息发布
            messageQueueWidget.publish(channel, broadcastMessage);
            Thread.sleep(2000);
        }
    }
}