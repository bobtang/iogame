package com.iohao.little.game.net.client;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.client.core.ServerSetting;
import com.iohao.little.game.net.message.common.ModuleKey;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 客户端服务器构建配置项
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
@Getter
@Setter
@Accessors(chain = true)
public class BoltClientServerSetting implements ServerSetting {
    /** ip:port */
    final String address;
    final ModuleKey moduleKey;
    final BarSkeleton barSkeleton;
    final ModuleMessage moduleMessage;
    final RemoteAddress remoteAddress;
    BoltClientProxy boltClientProxy;


    public BoltClientServerSetting(BarSkeleton barSkeleton, ModuleMessage moduleMessage, RemoteAddress remoteAddress) {
        this.barSkeleton = barSkeleton;
        this.moduleMessage = moduleMessage;
        this.remoteAddress = remoteAddress;
        this.moduleKey = moduleMessage.getModuleKey();
        this.address = remoteAddress.ip() + ":" + remoteAddress.port();
    }
}
