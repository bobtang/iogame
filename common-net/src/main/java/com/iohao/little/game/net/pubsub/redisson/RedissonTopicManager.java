package com.iohao.little.game.net.pubsub.redisson;

import com.iohao.little.game.net.pubsub.Consumer;
import com.iohao.little.game.net.pubsub.TopicInfo;
import com.iohao.little.game.net.pubsub.TopicManager;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

public class RedissonTopicManager implements TopicManager {
    RedissonClient redisson;



    <M> void add(TopicInfo topicInfo) {
        var topicName = topicInfo.getTopicName();
        RTopic topic = redisson.getTopic(topicName);

        Class messageClazz = topicInfo.getMessageClazz();
        Consumer messageConsumer = topicInfo.getMessageConsumer();

//        topic.addListener(messageClazz, messageConsumer);


    }
}
