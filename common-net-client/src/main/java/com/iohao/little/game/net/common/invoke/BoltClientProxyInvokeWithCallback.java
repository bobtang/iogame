package com.iohao.little.game.net.common.invoke;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.net.common.BoltClientProxy;

public class BoltClientProxyInvokeWithCallback {
    BoltClientProxy boltClientProxy;


    public void invokeWithCallback(Object request) throws RemotingException {
        RpcClient rpcClient = boltClientProxy.getRpcClient();

//        rpcClient.invokeWithCallback(connection, request, null, timeoutMillis);
    }
}
