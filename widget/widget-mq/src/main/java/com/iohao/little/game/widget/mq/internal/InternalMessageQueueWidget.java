package com.iohao.little.game.widget.mq.internal;

import com.iohao.little.game.widget.mq.AbstractMessageQueueWidget;
import com.iohao.little.game.widget.mq.MessageListenerWidget;
import com.iohao.little.game.widget.mq.MessageQueueConfigWidget;

/**
 * 逻辑服与网关
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public class InternalMessageQueueWidget extends AbstractMessageQueueWidget {

    public InternalMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        super(messageQueueConfigWidget);
    }

    @Override
    public void publish(String channel, Object message) {

    }

    @Override
    public void addMessageListener(MessageListenerWidget listener) {

    }

    @Override
    public void startup() {

    }
}
