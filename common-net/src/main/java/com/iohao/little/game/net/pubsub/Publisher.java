package com.iohao.little.game.net.pubsub;

public interface Publisher {
    void publish(String topic, Object data);
}
