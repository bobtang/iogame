package com.iohao.game.collect.tank;

import com.iohao.game.collect.common.GameBarSkeletonConfig;
import com.iohao.game.collect.common.ModuleKeyCont;
import com.iohao.game.collect.common.exception.GameCodeEnum;
import com.iohao.game.collect.logic.GameLogicCommonInit;
import com.iohao.game.collect.tank.action.TankAction;
import com.iohao.game.collect.tank.config.TankKit;
import com.iohao.game.collect.tank.send.TankSend;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.action.skeleton.core.exception.ActionErrorEnum;
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

    public static void main(String[] args) {
        TankClientStartupConfig config = new TankClientStartupConfig();
        config.createBarSkeleton();
    }

    @Override
    public BarSkeleton createBarSkeleton() {

        BarSkeletonBuilderParamConfig config = new BarSkeletonBuilderParamConfig()
                // 扫描 TankAction.class 所在包
                .addActionController(TankAction.class)
                // 推送消息-用于文档的生成
                .addActionSend(TankSend.class)
                // 错误码-用于文档的生成
                .addErrorCode(ActionErrorEnum.values())
                // 错误码-用于文档的生成
                .addErrorCode(GameCodeEnum.values());

        BarSkeletonBuilder builder = GameBarSkeletonConfig.createBuilder(config);

        return builder.build();
    }

    @Override
    public ModuleMessage createModuleMessage() {
        // 逻辑服的模块id，标记不同的逻辑服模块。
        int moduleId = ModuleKeyCont.gameTankModuleId;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        // 逻辑服的信息描述
        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 坦克");
        moduleMessage.setDescription("坦克业务");

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
