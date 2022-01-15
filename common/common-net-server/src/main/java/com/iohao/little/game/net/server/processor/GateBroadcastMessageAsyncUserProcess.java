package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.GateKit;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GateBroadcastMessageAsyncUserProcess extends AsyncUserProcessor<BroadcastMessage> {
    final WidgetComponents widgetComponents;

    public GateBroadcastMessageAsyncUserProcess(WidgetComponents widgetComponents) {
        this.widgetComponents = widgetComponents;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, BroadcastMessage broadcastMessage) {
        BoltServer boltServer = GateKit.getBoltServer();
        RpcServer rpcServer = boltServer.getRpcServer();

        // 转发给对外服务器
        ModuleInfoProxy externalModuleInfo = ModuleInfoManager.me().getExternalModuleInfo();
        String address = externalModuleInfo.getModuleMessage().getAddress();

        try {
            rpcServer.oneway(address, broadcastMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

//        externalModuleInfo.invokeSync(null);

//        // 模块之间的请求处理
//        MessageQueueWidget messageQueueWidget = widgetComponents.option(MessageQueueWidget.class);
//
//        // 逻辑服推送的消息
//        ResponseMessage responseMessage = broadcastMessage.getResponseMessage();
//        String channel = broadcastMessage.getChannel();
//
//        // 根据 channel 得到对应的消息处理
//        MessageListenerWidget messageListenerWidget = messageQueueWidget.getByChannel(channel);
//        messageListenerWidget.onMessage(responseMessage, channel, broadcastMessage);
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     * 假设 除了需要处理 MyRequest 类型的数据，还要处理 java.lang.String 类型，有两种方式：
     * 1、再提供一个 UserProcessor 实现类，其 interest() 返回 java.lang.String.class.getName()
     * 2、使用 MultiInterestUserProcessor 实现类，可以为一个 UserProcessor 指定 List<String> multiInterest()
     */
    @Override
    public String interest() {
        return BroadcastMessage.class.getName();
    }
}
