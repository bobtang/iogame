package com.iohao.little.game.net.client.common;

import com.alipay.remoting.Connection;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.ServerContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.common.ServerSender;
import com.iohao.little.game.widget.broadcast.BroadcastCont;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.net.message.common.InnerModuleMessage;
import com.iohao.little.game.widget.broadcast.MessageQueueWidget;
import com.iohao.little.game.widget.broadcast.internal.ClientBroadcastMessageContext;
import com.iohao.little.game.widget.config.WidgetComponents;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 客户端服务器代理, 持有一些属性
 * <pre>
 *     负责与网关通信
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-17
 * @see BoltClientServer#init()
 */
@Getter
@Setter
@Accessors(chain = true)
public class BoltClientProxy implements ServerContext, ServerSender {

    ClientBroadcastMessageContext broadcastMessageContext = new ClientBroadcastMessageContext(BoltClientProxy.this);

    Connection connection;

    /**
     * rpcClient
     * <pre>
     *     {@link BoltClientServer#init()}
     * </pre>
     */
    RpcClient rpcClient;

    /**
     * 业务框架
     * <pre>
     *     {@link BoltClientServer#init()}
     * </pre>
     */
    BarSkeleton barSkeleton;

    /** 消息发送超时时间 */
    int timeoutMillis = 1000;

    final WidgetComponents widgetComponents = new WidgetComponents();

    public Object invokeSync(final Object request, final int timeoutMillis) throws RemotingException, InterruptedException {
        return rpcClient.invokeSync(connection, request, timeoutMillis);
    }

    public Object invokeSync(final Object request) throws RemotingException, InterruptedException {
        return invokeSync(request, timeoutMillis);
    }

    public void oneway(final Object request) throws RemotingException {
        this.rpcClient.oneway(connection, request);
    }

    public void invokeWithCallback(Object request) throws RemotingException {
        this.rpcClient.invokeWithCallback(connection, request, null, timeoutMillis);
    }

    public void broadcast(ResponseMessage data) {
        broadcast(data.getUserId(), data);
    }

    public void broadcast(ResponseMessage responseMessage, List<Long> userIdList) {
        // 广播上下文
        BroadcastMessage broadcastMessage = new BroadcastMessage();
        broadcastMessage.setChannel(BroadcastCont.defaultChannel);
        broadcastMessage.setResponseMessage(responseMessage);
        broadcastMessage.setContext(broadcastMessageContext);
        broadcastMessage.setUserIdList(userIdList);

        // 广播小部件
        MessageQueueWidget messageQueueWidget = widgetComponents.option(MessageQueueWidget.class);
        messageQueueWidget.publish(broadcastMessage);
    }

    /**
     * 给单个用户广播
     *
     * @param userId          userId -1 给全体广播
     * @param responseMessage 消息
     */
    public void broadcast(long userId, ResponseMessage responseMessage) {
        if (userId > 0) {
            responseMessage.setUserId(userId);
        }

        // 广播上下文
        BroadcastMessage broadcastMessage = new BroadcastMessage();
        broadcastMessage.setChannel(BroadcastCont.defaultChannel);
        broadcastMessage.setResponseMessage(responseMessage);
        broadcastMessage.setContext(broadcastMessageContext);
        broadcastMessage.setBroadcastAll(userId == -1);

        // 广播小部件
        MessageQueueWidget messageQueueWidget = widgetComponents.option(MessageQueueWidget.class);
        messageQueueWidget.publish(broadcastMessage);
    }

    /**
     * 请求其它子服务器的数据
     *
     * @param cmdInfo cmdInfo
     * @param data    请求参数
     * @return ResponseMessage.data
     */
    public Object invokeModuleMessageData(CmdInfo cmdInfo, Object data) {
        ResponseMessage responseMessage = invokeModuleMessage(cmdInfo, data);
        return responseMessage.getData();
    }

    /**
     * 请求其它子服务器的数据
     *
     * @param cmdInfo cmdInfo
     * @param data    请求参数
     * @return ResponseMessage
     */
    public ResponseMessage invokeModuleMessage(CmdInfo cmdInfo, Object data) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);
        requestMessage.setData(data);
        return invokeModuleMessage(requestMessage);
    }

    /**
     * 请求其它子服务器的数据
     *
     * @param requestMessage requestMessage
     * @return ResponseMessage
     */
    public ResponseMessage invokeModuleMessage(RequestMessage requestMessage) {

        InnerModuleMessage moduleMessage = new InnerModuleMessage();
        moduleMessage.setRequestMessage(requestMessage);

        ResponseMessage o = null;

        try {
            o = (ResponseMessage) this.invokeSync(moduleMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

        return o;
    }

    @Override
    public void sendResponse(Object request) {
        try {
            rpcClient.oneway(connection, request);
        } catch (RemotingException e) {
            e.printStackTrace();
        }
    }
}
