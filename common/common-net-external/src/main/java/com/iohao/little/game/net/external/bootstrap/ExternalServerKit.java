package com.iohao.little.game.net.external.bootstrap;

import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.net.client.BoltClientServer;
import lombok.experimental.UtilityClass;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
@UtilityClass
public class ExternalServerKit {
    public RpcClient rpcClient;
    public BoltClientServer boltClientServer;

    public String address() {
        return boltClientServer.getBoltClientServerSetting().getAddress();
    }
}
