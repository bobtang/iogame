package com.iohao.game.collect.external;

import com.alipay.remoting.rpc.RpcClient;
import com.iohao.game.collect.common.GameBarSkeletonConfig;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.external.bolt.ExternalBroadcastMessageAsyncUserProcessor;
import com.iohao.little.game.net.external.bolt.ExternalChangeUserIdMessageAsyncUserProcessor;
import com.iohao.little.game.net.external.bolt.ExternalResponseMessageAsyncUserProcessor;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleType;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public class GameExternalClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        BarSkeletonBuilderParamConfig config = new BarSkeletonBuilderParamConfig();

        BarSkeletonBuilder builder = GameBarSkeletonConfig.createBuilder(config);

        return builder.build();
    }

    @Override
    public ModuleMessage createModuleMessage() {
        int moduleId = 0;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleType(ModuleType.EXTERNAL);
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("对外服务器(external)");
        moduleMessage.setDescription("对接真实的游戏用户");

        return moduleMessage;
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        int port = GameConfig.gatePort;
        String ip = GameConfig.gateIp;
        return new RemoteAddress(ip, port);
    }

    @Override
    public void widgetComponents(WidgetComponents widgetComponents) {

    }

    @Override
    public void registerUserProcessor(BoltClientServer boltClientServer) {
//        ClientStartupConfig.super.registerUserProcessor(boltClientServer);

        RpcClient rpcClient = boltClientServer.getRpcClient();

        // 注册 广播处理器
        ExternalBroadcastMessageAsyncUserProcessor broadcastMessageAsyncUserProcessor = new ExternalBroadcastMessageAsyncUserProcessor();
        rpcClient.registerUserProcessor(broadcastMessageAsyncUserProcessor);

        // 注册 用户id变更处理
        ExternalChangeUserIdMessageAsyncUserProcessor changeUserIdMessageAsyncUserProcessor = new ExternalChangeUserIdMessageAsyncUserProcessor();
        rpcClient.registerUserProcessor(changeUserIdMessageAsyncUserProcessor);

        // 注册 接收网关消息处理
        ExternalResponseMessageAsyncUserProcessor responseMessageAsyncUserProcessor = new ExternalResponseMessageAsyncUserProcessor();
        rpcClient.registerUserProcessor(responseMessageAsyncUserProcessor);

    }

    @Override
    public void startupSuccess(BoltClientServer boltClientServer) {
        ExternalServerKit.setBoltClientServer(boltClientServer);
    }
}
