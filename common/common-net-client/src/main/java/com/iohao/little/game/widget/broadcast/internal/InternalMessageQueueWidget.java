package com.iohao.little.game.widget.broadcast.internal;

import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.client.common.BoltClientProxyManager;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.widget.broadcast.AbstractMessageQueueWidget;
import com.iohao.little.game.widget.broadcast.MessageListenerWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueConfigWidget;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 逻辑服与网关
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
public class InternalMessageQueueWidget extends AbstractMessageQueueWidget {


    public InternalMessageQueueWidget(MessageQueueConfigWidget messageQueueConfigWidget) {
        super(messageQueueConfigWidget);
    }

    @Override
    public void publish(String channel, BroadcastMessage message) {
        BroadcastMessage broadcastMessage = message;
        ResponseMessage responseMessage = broadcastMessage.getResponseMessage();
        CmdInfo cmdInfo = responseMessage.getCmdInfo();

        BoltClientProxy boltClientProxy = BoltClientProxyManager.me().getBoltClientProxy(cmdInfo);

        try {
            boltClientProxy.oneway(broadcastMessage);
        } catch (RemotingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addMessageListener(MessageListenerWidget listener) {
        CharSequence channel = listener.channel();

        listenerWidgetMap.put(channel, listener);
    }

    @Override
    public MessageListenerWidget getByChannel(String channel) {
        return listenerWidgetMap.get(channel);
    }

    @Override
    public void startup() {

    }
}
