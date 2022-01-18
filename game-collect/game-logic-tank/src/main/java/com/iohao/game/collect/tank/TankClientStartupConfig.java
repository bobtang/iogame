package com.iohao.game.collect.tank;

import com.iohao.game.collect.common.GameBarSkeletonConfig;
import com.iohao.game.collect.common.ModuleKeyCont;
import com.iohao.game.collect.logic.GameLogicCommonInit;
import com.iohao.game.collect.tank.action.TankAction;
import com.iohao.game.collect.tank.config.TankKit;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
public class TankClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {

        // 扫描 TankAction.class 所在包
        BarSkeletonBuilder builder = GameBarSkeletonConfig.createBuilder(TankAction.class);

        return builder.build();
    }

    @Override
    public ModuleMessage createModuleMessage() {

        int moduleId = ModuleKeyCont.userModuleId;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 用户");
        moduleMessage.setDescription("用户业务");

        return moduleMessage;
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        return GameLogicCommonInit.createRemoteAddress();
    }

    @Override
    public void startupSuccess(BoltClientServer boltClientServer) {
        TankKit.boltClientProxy = boltClientServer
                .getBoltClientServerSetting()
                .getBoltClientProxy();
    }
}
