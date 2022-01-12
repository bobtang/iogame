package com.iohao.little.game.widget.broadcast.internal;

import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.server.GateKit;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.widget.broadcast.MessageListenerWidget;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.config.WidgetComponents;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
@Slf4j
public class GateBroadcastMessageListenerWidget implements MessageListenerWidget<ResponseMessage> {
    @Override
    public CharSequence channel() {
        return "internal_channel";
    }

    @Override
    public void onMessage(ResponseMessage responseMessage, CharSequence channel, BroadcastMessage broadcastMessage) {

        WidgetComponents widgetComponents = GateKit.boltServer.getWidgetComponents();
        MessageQueueWidget option = widgetComponents.option(MessageQueueWidget.class);
        log.info("当前使用的发布订阅内核是: {}", option.getClass());
        log.info("网关 Broadcast 正在处理 {}", responseMessage);

        // TODO: 2022/1/12 转发到对外服务器
        ModuleInfoProxy externalModuleInfo = ModuleInfoManager.me().getExternalModuleInfo();

        try {
            externalModuleInfo.invokeSync(responseMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
