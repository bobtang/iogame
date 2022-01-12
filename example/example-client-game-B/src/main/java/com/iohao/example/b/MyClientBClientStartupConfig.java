package com.iohao.example.b;

import com.iohao.example.b.action.BookAction;
import com.iohao.example.client.InitClientCommon;
import com.iohao.example.common.ExampleModuleKeyCont;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.client.common.ClientBarSkeleton;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;

public class MyClientBClientStartupConfig implements ClientStartupConfig {
    @Override
    public BarSkeleton createBarSkeleton() {
        // 扫描 BookAction.class 所在包
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(BookAction.class);
        return barSkeleton;
    }

    @Override
    public ModuleMessage createModuleMessage() {

        int moduleId = ExampleModuleKeyCont.ModuleB.moduleId;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 B");
        moduleMessage.setDescription("五子棋");

        return moduleMessage;
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        return InitClientCommon.createRemoteAddress();
    }


}
