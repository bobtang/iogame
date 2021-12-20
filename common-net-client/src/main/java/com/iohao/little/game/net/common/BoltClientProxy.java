package com.iohao.little.game.net.common;

import com.alipay.remoting.Connection;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.ServerContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.core.ServerSender;
import com.iohao.little.game.net.message.common.BroadcastMessage;
import com.iohao.little.game.net.message.common.InnerModuleMessage;
import com.iohao.little.game.widget.config.WidgetComponents;
import com.iohao.little.game.widget.mq.MessageQueueWidget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 客户端服务器代理, 持有一些属性
 * <pre>
 *     负责与网关通信
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
@Getter
@Setter
@Accessors(chain = true)
public class BoltClientProxy implements ServerContext, ServerSender {
    RpcClient rpcClient;
    Connection connection;
    BarSkeleton barSkeleton;
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

    public void broadcast(long userId, ResponseMessage responseMessage) {
        responseMessage.setUserId(userId);

        // TODO: 2021-12-14 广播
        String channel = "internal_channel";
        BroadcastMessage broadcastMessage = new BroadcastMessage();
        broadcastMessage.setChannel(channel);
        broadcastMessage.setResponseMessage(responseMessage);

        MessageQueueWidget messageQueueWidget = widgetComponents.option(MessageQueueWidget.class);
        messageQueueWidget.publish(channel, broadcastMessage);
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

}
