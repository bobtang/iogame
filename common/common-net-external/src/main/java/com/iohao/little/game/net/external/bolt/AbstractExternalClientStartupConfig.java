package com.iohao.little.game.net.external.bolt;

import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.external.bootstrap.ExternalServerKit;

/**
 * 抽象对外服启动流程
 *
 * @author 洛朱
 * @date 2022-02-04
 */
public abstract class AbstractExternalClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        return builder.build();
    }

    @Override
    public void registerUserProcessor(BoltClientServer boltClientServer) {

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
