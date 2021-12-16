package com.iohao.little.game.net.pubsub;

import lombok.Data;

@Data
public class TopicInfo {
    String topicName;
    Class<?> messageClazz;
    Consumer messageConsumer;
}
