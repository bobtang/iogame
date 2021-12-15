package com.iohao.little.game.net;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ModuleKey;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BoltClientServerSetting {
    final String ip;
    final int port;
    /** ip:port */
    final String address;
    ModuleKey moduleKey;
    BarSkeleton barSkeleton;
    ModuleMessage moduleMessage;
    BoltClientProxy boltClientProxy;

    public BoltClientServerSetting(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.address = ip + ":" + port;
    }


    public BoltClientServer build() {
        return new BoltClientServer(this);
    }

}
