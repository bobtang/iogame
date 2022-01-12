package com.iohao.little.game.net.client;

import com.alipay.remoting.config.BoltClientOption;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.ActionCommandManager;
import com.iohao.little.game.net.client.common.BoltClientProxyManager;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

/**
 * 客户端服务器
 * <pre>
 *     这里指逻辑服
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Slf4j
public class BoltClientServer {

    @Getter
    final RpcClient rpcClient;
    @Getter
    final BoltClientServerSetting boltClientServerSetting;

    public BoltClientServer(BoltClientServerSetting boltClientServerSetting) {
        this.boltClientServerSetting = boltClientServerSetting;
        rpcClient = new RpcClient();
        rpcClient.option(BoltClientOption.CONN_RECONNECT_SWITCH, true);
        rpcClient.option(BoltClientOption.CONN_MONITOR_SWITCH, true);
    }

    public void init() {
        var barSkeleton = boltClientServerSetting.barSkeleton;
        var moduleKey = boltClientServerSetting.moduleKey;

        // cmd 与 moduleKey 关联
        ActionCommandManager actionCommandManager = barSkeleton.getActionCommandManager();
        var cmdMergeArray = actionCommandManager.arrayCmdMerge();
        ModuleKeyKit.relation(cmdMergeArray, moduleKey);

        // boltClient 代理设置 rpcClient
        var boltClientProxy = BoltClientProxyManager.me().getBoltClientProxy(moduleKey);
        boltClientProxy.setRpcClient(rpcClient);
        boltClientProxy.setBarSkeleton(barSkeleton);
        boltClientServerSetting.boltClientProxy = boltClientProxy;
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
