package com.iohao.little.game.widget.broadcast;

public abstract class AbstractMessageQueueWidget implements MessageQueueWidget {
    MessageQueueConfigWidget messageQueueConfigWidget;

    public AbstractMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        this.messageQueueConfigWidget = messageQueueConfigWidget;
    }
}
