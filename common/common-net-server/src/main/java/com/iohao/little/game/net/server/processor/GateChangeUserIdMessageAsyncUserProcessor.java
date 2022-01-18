package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.message.common.ChangeUserIdMessage;
import com.iohao.little.game.net.message.common.ChangeUserIdMessageResponse;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
@Slf4j
public class GateChangeUserIdMessageAsyncUserProcessor extends AsyncUserProcessor<ChangeUserIdMessage> {

    final BoltServer boltServer;

    public GateChangeUserIdMessageAsyncUserProcessor(BoltServer boltServer) {
        this.boltServer = boltServer;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ChangeUserIdMessage changeUserIdMessage) {
        // 用户 id 变更
        RpcServer rpcServer = boltServer.getRpcServer();

        // 转发 给 对外服务器
        ModuleInfoProxy externalModuleInfo = ModuleInfoManager.me().getExternalModuleInfo();
        String address = externalModuleInfo.getModuleMessage().getAddress();

        log.info(" 用户 id 变更 （逻辑服 --> 网关 --> 对外服 --> 网关 --> 逻辑服） : {}", changeUserIdMessage);

        try {
            log.info("2 网关");
            ChangeUserIdMessageResponse messageResponse = (ChangeUserIdMessageResponse) rpcServer
                    .invokeSync(address, changeUserIdMessage, 1000);

            log.info("4 网关");
            asyncCtx.sendResponse(messageResponse);

        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String interest() {
        return ChangeUserIdMessage.class.getName();
    }
}
