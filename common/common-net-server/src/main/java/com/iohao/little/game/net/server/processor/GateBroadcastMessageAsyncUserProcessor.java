package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import com.iohao.little.game.widget.broadcast.BroadcastMessage;
import com.iohao.little.game.widget.config.WidgetComponents;
import lombok.extern.slf4j.Slf4j;

/**
 * 把逻辑服的广播转发到对外服
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@Slf4j
public class GateBroadcastMessageAsyncUserProcessor extends AsyncUserProcessor<BroadcastMessage> {
    final WidgetComponents widgetComponents;
    final BoltServer boltServer;

    public GateBroadcastMessageAsyncUserProcessor(BoltServer boltServer, WidgetComponents widgetComponents) {
        this.widgetComponents = widgetComponents;
        this.boltServer = boltServer;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, BroadcastMessage broadcastMessage) {
        log.info("把逻辑服的广播转发到对外服 {}", broadcastMessage);
        // TODO: 2022/1/18 广播上下文是有问题的

        // 转发 给 对外服务器
        ModuleInfoProxy externalModuleInfo = ModuleInfoManager.me().getExternalModuleInfo();
        String address = externalModuleInfo.getModuleMessage().getAddress();

        try {
            RpcServer rpcServer = boltServer.getRpcServer();
            rpcServer.oneway(address, broadcastMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
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
