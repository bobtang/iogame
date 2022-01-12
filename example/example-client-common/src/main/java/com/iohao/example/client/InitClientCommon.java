package com.iohao.example.client;

import com.iohao.example.common.ExampleCont;
import com.iohao.little.game.net.client.core.RemoteAddress;
import lombok.experimental.UtilityClass;

/**
 * 示例， 抽出一些公共的 clientServer 配置代码
 */
@UtilityClass
public class InitClientCommon {
    public RemoteAddress createRemoteAddress() {
        String ip = ExampleCont.ip;
        int port = ExampleCont.port;
        return new RemoteAddress(ip, port);
    }
}
