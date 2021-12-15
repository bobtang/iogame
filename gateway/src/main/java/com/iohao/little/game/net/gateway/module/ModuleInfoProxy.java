package com.iohao.little.game.net.gateway.module;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.net.gateway.GateKit;
import com.iohao.little.game.net.message.common.ModuleMessage;

public class ModuleInfoProxy {

    final ModuleMessage moduleMessage;

    public ModuleInfoProxy(ModuleMessage moduleMessage) {
        this.moduleMessage = moduleMessage;
    }


    public Object invokeSync(RequestMessage requestMessage) throws RemotingException, InterruptedException {
        String address = moduleMessage.getAddress();

        RpcServer rpcServer = GateKit.boltServer.getRpcServer();
        Object result = rpcServer.invokeSync(address, requestMessage, 1000);

        return result;
    }
}
