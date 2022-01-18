package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * 把逻辑服的响应转发到对外服
 *
 * @author 洛朱
 * @date 2022-01-18
 */
@Slf4j
public class GateResponseMessageAsyncUserProcessor extends AsyncUserProcessor<ResponseMessage> {

    final BoltServer boltServer;

    public GateResponseMessageAsyncUserProcessor(BoltServer boltServer) {
        this.boltServer = boltServer;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ResponseMessage responseMessage) {
        log.info("把逻辑服的响应转发到对外服 {}", responseMessage);

        // 转发 给 对外服务器
        ModuleInfoProxy externalModuleInfo = ModuleInfoManager.me().getExternalModuleInfo();
        String address = externalModuleInfo.getModuleMessage().getAddress();

        try {
            RpcServer rpcServer = boltServer.getRpcServer();
            rpcServer.oneway(address, responseMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String interest() {
        return ResponseMessage.class.getName();
    }
}
