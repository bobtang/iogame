package com.iohao.little.game.net.external;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.BoltClientServerSetting;
import com.iohao.little.game.net.client.common.ClientBarSkeleton;
import com.iohao.little.game.net.client.common.ClientConnectEventProcessor;
import com.iohao.little.game.net.client.common.ClientDisConnectEventProcessor;
import com.iohao.little.game.net.client.common.ClientRequestMessageAsyncUserProcessor;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public class ExternalClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 扫描 AppleAction.class 所在包
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(ExternalClientStartupConfig.class);
        return barSkeleton;
    }

    @Override
    public ModuleMessage createModuleMessage() {
        int moduleId = 0;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("对外服务器(external)");
        moduleMessage.setDescription("对接真实的游戏用户");

        return moduleMessage;
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        int port = 8803;
        String ip = "127.0.0.1";
        return new RemoteAddress(ip, port);
    }

    @Override
    public void serverSetting(BoltClientServerSetting setting) {

    }

    @Override
    public void connectionEventProcessor(BoltClientServer boltClientServer) {
        ClientConnectEventProcessor clientConnectEventProcessor = new ClientConnectEventProcessor();
        ClientDisConnectEventProcessor clientDisConnectProcessor = new ClientDisConnectEventProcessor();

        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();
        var moduleKey = boltClientServerSetting.getModuleKey();
        clientConnectEventProcessor.setModuleKey(moduleKey);

        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectEventProcessor);
        rpcClient.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);

        ExternalServerKit.boltClientServer = boltClientServer;
        ExternalServerKit.rpcClient = boltClientServer.getRpcClient();
    }

    @Override
    public void registerUserProcessor(BoltClientServer boltClientServer) {
        BoltClientServerSetting boltClientServerSetting = boltClientServer.getBoltClientServerSetting();

        ClientRequestMessageAsyncUserProcessor processor = new ClientRequestMessageAsyncUserProcessor();
        processor.setBoltClientServerSetting(boltClientServerSetting);

        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.registerUserProcessor(processor);
    }

    @Override
    public void widgetComponents(WidgetComponents widgetComponents) {

    }
}
