package com.iohao.little.game.widget.mq;

public abstract class AbstractMessageQueueWidget implements MessageQueueWidget {
    MessageQueueConfigWidget messageQueueConfigWidget;

    public AbstractMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        this.messageQueueConfigWidget = messageQueueConfigWidget;
    }
}
