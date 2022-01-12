package com.iohao.game.collect.external;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.client.common.ClientBarSkeleton;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleType;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public class ExternalClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 扫描 AppleAction.class 所在包
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(ExternalClientStartupConfig.class);
        return barSkeleton;
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
        int port = 8803;
        String ip = "127.0.0.1";
        return new RemoteAddress(ip, port);
    }
}
