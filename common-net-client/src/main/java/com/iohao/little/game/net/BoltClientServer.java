package com.iohao.little.game.net;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.BoltClientOption;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.net.common.BoltClientProxyManager;
import com.iohao.little.game.net.common.ClientConnectEventProcessor;
import com.iohao.little.game.net.common.ClientDisConnectEventProcessor;
import com.iohao.little.game.net.common.ClientRequestMessageAsyncUserProcessor;
import com.iohao.little.game.net.message.common.ModuleKeyManager;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class BoltClientServer {

    RpcClient rpcClient;

    ClientConnectEventProcessor clientConnectEventProcessor = new ClientConnectEventProcessor();
    ClientDisConnectEventProcessor clientDisConnectProcessor = new ClientDisConnectEventProcessor();
    ClientRequestMessageAsyncUserProcessor processor = new ClientRequestMessageAsyncUserProcessor();

    final BoltClientServerSetting boltClientServerSetting;

    public BoltClientServer(BoltClientServerSetting boltClientServerSetting) {
        this.boltClientServerSetting = boltClientServerSetting;
    }

    public void init() {
        var barSkeleton = boltClientServerSetting.barSkeleton;
        var moduleKey = boltClientServerSetting.moduleKey;
        processor.setBoltClientServerSetting(boltClientServerSetting);

        clientConnectEventProcessor.setModuleKey(moduleKey);

        // cmd 与 moduleKey 关联
        var cmdMergeArray = barSkeleton.getActionCommandManager().arrayCmdMerge();
        ModuleKeyManager.me().relation(cmdMergeArray, moduleKey);

        rpcClient = new RpcClient();
        rpcClient.option(BoltClientOption.CONN_RECONNECT_SWITCH, true);
        rpcClient.option(BoltClientOption.CONN_MONITOR_SWITCH, true);

        // 设置 rpcClient
        var boltClientProxy = BoltClientProxyManager.me().getBoltClientProxy(moduleKey);
        boltClientProxy.setRpcClient(rpcClient);
        boltClientProxy.setBarSkeleton(barSkeleton);
        boltClientServerSetting.boltClientProxy = boltClientProxy;


        rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectEventProcessor);
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);

        rpcClient.registerUserProcessor(processor);
        rpcClient.startup();
    }

    /**
     * 注册到网关
     */
    public void registerModuleToGate() {
        ModuleMessage moduleMessage = boltClientServerSetting.moduleMessage;
        try {
            this.rpcClient.oneway(boltClientServerSetting.address, moduleMessage);
            Thread.sleep(100);
        } catch (RemotingException | InterruptedException e) {
            String errMsg = "RemotingException caught in oneway!";
            Assert.fail(errMsg);
        }

    }


}
