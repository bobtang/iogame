package com.iohao.little.game.net.message.common;

import com.iohao.little.game.widget.config.WidgetOption;
import com.iohao.little.game.widget.mq.MessageQueueWidget;

public interface WidgetCont {
    WidgetOption<MessageQueueWidget> MESSAGE_QUEUE = WidgetOption.valueOf("message_queue");
}
