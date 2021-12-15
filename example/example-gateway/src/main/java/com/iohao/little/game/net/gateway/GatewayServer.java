package com.iohao.little.game.net.gateway;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.config.Configs;
import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.BoltServer;
import com.iohao.little.game.net.gateway.common.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <BR>
 */
@Slf4j
public class GatewayServer {
    BoltServer server;
    int port = ExampleCont.port;

    GateConnectEventProcessor serverConnectProcessor = new GateConnectEventProcessor();
    GateDisConnectEventProcessor serverDisConnectProcessor = new GateDisConnectEventProcessor();

    public void init() {
        System.setProperty(Configs.CONN_MONITOR_SWITCH, "true");
        System.setProperty(Configs.CONN_RECONNECT_SWITCH, "true");

        server = new BoltServer(port, true);
        GateKit.boltServer = server;

        serverConnectProcessor.setServer(server);
        server.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        server.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);

        MyServerAsyncUserProcessor processor = new MyServerAsyncUserProcessor();
        server.registerUserProcessor(processor);

        GateModuleMessageAsyncUserProcessor registerAsyncUserProcessor = new GateModuleMessageAsyncUserProcessor();
        server.registerUserProcessor(registerAsyncUserProcessor);

        GateInnerModuleMessageAsyncUserProcess innerModuleMessageAsyncUserProcess = new GateInnerModuleMessageAsyncUserProcess();
        server.registerUserProcessor(innerModuleMessageAsyncUserProcess);

        log.info("start server ok!");



        /**
         * 启动服务端：先做 netty 配置初始化操作，再做 bind 操作
         * 配置 netty 参数两种方式：[SOFABolt 源码分析11 - Config 配置管理的设计](https://www.jianshu.com/p/76b0be893745)
         */
        server.start();
    }

    public static void main(String[] args) {
        // 启动网关
        GatewayServer gatewayServe = new GatewayServer();
        gatewayServe.init();
    }


}
