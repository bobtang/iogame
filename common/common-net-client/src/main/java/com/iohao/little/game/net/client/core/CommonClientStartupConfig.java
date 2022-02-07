package com.iohao.little.game.net.client.core;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.BoltClientServerSetting;
import com.iohao.little.game.net.client.common.ClientConnectEventProcessor;
import com.iohao.little.game.net.client.common.ClientDisConnectEventProcessor;
import com.iohao.little.game.net.client.common.ClientRequestMessageAsyncUserProcessor;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
class CommonClientStartupConfig {
    public static void configBroadcastWidgetComponents(WidgetComponents widgetComponents) {

    }

    public static void registerUserProcessor(BoltClientServer boltClientServer) {
        RpcClient rpcClient = boltClientServer.getRpcClient();

        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();
        ClientRequestMessageAsyncUserProcessor processor = new ClientRequestMessageAsyncUserProcessor();
        processor.setBoltClientServerSetting(boltClientServerSetting);


        rpcClient.registerUserProcessor(processor);
    }

    public static void connectionEventProcessor(BoltClientServer boltClientServer) {

        ClientConnectEventProcessor clientConnectEventProcessor = new ClientConnectEventProcessor();
        ClientDisConnectEventProcessor clientDisConnectProcessor = new ClientDisConnectEventProcessor();

        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();
        var moduleKey = boltClientServerSetting.getModuleKey();
        clientConnectEventProcessor.setModuleKey(moduleKey);

        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectEventProcessor);
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);
    }

}
