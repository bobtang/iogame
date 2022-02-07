package com.iohao.game.collect.external;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.external.bolt.AbstractExternalClientStartupConfig;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleType;

/**
 * 游戏对外服的内部逻辑服
 * <pre>
 *     主要职责：转发用户请求到网关服，在由网关服转到具体的业务逻辑服
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-11
 */
public class GameExternalClientStartupConfig extends AbstractExternalClientStartupConfig {

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
}
