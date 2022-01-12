package com.iohao.little.game.net.gateway;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.Configs;
import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.gateway.common.ExampleGateBroadcastMessageAsyncUserProcess;
import com.iohao.little.game.net.gateway.common.ExampleGateConnectEventProcessor;
import com.iohao.little.game.net.server.common.GateDisConnectEventProcessor;
import com.iohao.little.game.net.server.common.GateInnerModuleMessageAsyncUserProcess;
import com.iohao.little.game.net.server.common.GateModuleMessageAsyncUserProcessor;
import com.iohao.little.game.net.server.core.ServerStartupConfig;
import com.iohao.little.game.widget.config.WidgetComponents;

public class ExampleGatewayServerStartupConfig implements ServerStartupConfig {
    @Override
    public BoltServer createBoltServer() {
        // 开启 bolt 重连
        System.setProperty(Configs.CONN_MONITOR_SWITCH, "true");
        System.setProperty(Configs.CONN_RECONNECT_SWITCH, "true");

        int port = ExampleCont.port;

        return new BoltServer(port, true);
    }

    @Override
    public void connectionEventProcessor(BoltServer boltServer) {
        ExampleGateConnectEventProcessor serverConnectProcessor = new ExampleGateConnectEventProcessor();
        GateDisConnectEventProcessor serverDisConnectProcessor = new GateDisConnectEventProcessor();

        serverConnectProcessor.setServer(boltServer);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        boltServer.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);
    }

    @Override
    public void registerUserProcessor(BoltServer boltServer) {
        // 处理 - 模块注册
        var registerAsyncUserProcessor = new GateModuleMessageAsyncUserProcessor();
        boltServer.registerUserProcessor(registerAsyncUserProcessor);

        // 处理 - 内部模块转发
        var innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcess();
        boltServer.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        // 处理 - 发布订阅
        WidgetComponents widgetComponents = boltServer.getWidgetComponents();
        var gateBroadcastMessageAsyncUserProcess = new ExampleGateBroadcastMessageAsyncUserProcess(widgetComponents);
        boltServer.registerUserProcessor(gateBroadcastMessageAsyncUserProcess);
    }

}
