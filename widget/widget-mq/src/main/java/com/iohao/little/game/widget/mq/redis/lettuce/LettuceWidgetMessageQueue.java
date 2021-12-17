//package com.iohao.little.game.widget.mq.redis.lettuce;
//
//import com.alibaba.fastjson.JSON;
//import com.iohao.little.game.widget.config.WidgetOptions;
//import com.iohao.little.game.widget.mq.AbstractWidgetMessageQueue;
//import com.iohao.little.game.widget.mq.WidgetMessageExt;
//import com.iohao.little.game.widget.mq.WidgetMessageListener;
//import com.iohao.little.game.widget.mq.WidgetMessageQueueConfig;
//import io.lettuce.core.RedisClient;
//import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
//
//public class LettuceWidgetMessageQueue extends AbstractWidgetMessageQueue {
//    public LettuceWidgetMessageQueue(WidgetMessageQueueConfig widgetMessageQueueConfig) {
//        super(widgetMessageQueueConfig);
//
//        WidgetOptions widgetOptions = widgetMessageQueueConfig.getWidgetOptions();
//
//        client = RedisClient.create("redis://localhost");
//        connection = client.connectPubSub().sync();
//    }
//
//    @Override
//    public void publish(String channel, Object message) {
//        WidgetMessageExt widgetMessageExt = new WidgetMessageExt();
//        widgetMessageExt.setData(message);
//        var jsonString = JSON.toJSONString(widgetMessageExt);
//        connection.publish(channel, jsonString);
//    }
//
//    @Override
//    public void addMessageListener(WidgetMessageListener< Object> listener) {
//        CharSequence channel = listener.channel();
//
////        RTopic topic = redisson.getTopic(channel.toString());
////        MessageListenerProxy messageListenerProxy = new MessageListenerProxy(listener);
////        topic.addListener(WidgetMessageExt.class, messageListenerProxy);
//    }
//
//    RedisClient client;
//    RedisPubSubCommands<String, String> connection;
//
//    @Override
//    public void startup() {
//        // TODO: 2021/12/16
//
//    }
//}
