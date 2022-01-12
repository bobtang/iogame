package com.iohao.example.a;

import com.iohao.example.a.action.AppleAction;
import com.iohao.example.client.InitClientCommon;
import com.iohao.example.common.ExampleModuleKeyCont;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.example.client.ExampleClientBarSkeleton;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;

public class MyClientAClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 扫描 AppleAction.class 所在包
        BarSkeleton barSkeleton = ExampleClientBarSkeleton.newBarSkeleton(AppleAction.class);
        return barSkeleton;
    }

    @Override
    public ModuleMessage createModuleMessage() {

        int moduleId = ExampleModuleKeyCont.ModuleA.moduleId;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 A");
        moduleMessage.setDescription("做大厅业务");

        return moduleMessage;
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        return InitClientCommon.createRemoteAddress();
    }
}
