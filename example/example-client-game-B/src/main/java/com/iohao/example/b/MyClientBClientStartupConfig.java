package com.iohao.example.b;

import com.iohao.example.b.action.BookAction;
import com.iohao.example.client.ClientWidgetComponentsInit;
import com.iohao.example.common.ExampleCont;
import com.iohao.example.common.ModuleKeyCont;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.BoltClientServerSetting;
import com.iohao.little.game.net.common.ClientBarSkeleton;
import com.iohao.little.game.net.core.ClientStartupConfig;
import com.iohao.little.game.net.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyManager;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.widget.config.WidgetComponents;

public class MyClientBClientStartupConfig implements ClientStartupConfig {
    @Override
    public RemoteAddress createRemoteAddress() {
        String ip = ExampleCont.ip;
        int port = ExampleCont.port;
        return new RemoteAddress(ip, port);
    }

    @Override
    public BarSkeleton createBarSkeleton() {
        // 扫描 BookAction.class 所在包
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(BookAction.class);

        return barSkeleton;
    }

    @Override
    public ModuleMessage createModuleMessage() {

        int moduleId = ModuleKeyCont.ModuleB.moduleId;
        var moduleKey = ModuleKeyManager.me().getModuleKey(moduleId);

        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 B");
        moduleMessage.setDescription("五子棋");

        return moduleMessage;
    }

    @Override
    public void serverSetting(BoltClientServerSetting setting) {

    }

    @Override
    public void widgetComponents(WidgetComponents widgetComponents) {
        // 初始化小部件
        new ClientWidgetComponentsInit().widgetComponents(widgetComponents);
    }
}
