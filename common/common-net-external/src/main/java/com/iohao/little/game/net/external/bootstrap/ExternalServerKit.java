package com.iohao.little.game.net.external.bootstrap;

import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.BoltClientServerSetting;
import lombok.experimental.UtilityClass;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
@UtilityClass
public class ExternalServerKit {
    /** 网关 rpcClient */
    public RpcClient rpcClient;
    /** 网关 BoltClientServer */
    public BoltClientServer boltClientServer;
    /** 网关 BoltClientServerSetting */
    public BoltClientServerSetting boltClientServerSetting;

    public void setBoltClientServer(BoltClientServer boltClientServer) {
        ExternalServerKit.rpcClient = boltClientServer.getRpcClient();
        ExternalServerKit.boltClientServer = boltClientServer;
        ExternalServerKit.boltClientServerSetting = boltClientServer.getBoltClientServerSetting();
    }

    public String address() {
        return boltClientServerSetting.getAddress();
    }
}
