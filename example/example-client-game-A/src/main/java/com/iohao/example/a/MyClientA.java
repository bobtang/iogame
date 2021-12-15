package com.iohao.example.a;

import com.iohao.example.a.action.AppleAction;
import com.iohao.example.common.ExampleCont;
import com.iohao.example.common.ModuleKeyCont;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.BoltClientServer;
import com.iohao.little.game.net.BoltClientServerSetting;
import com.iohao.little.game.net.common.ClientBarSkeleton;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleKey;
import com.iohao.little.game.net.message.common.ModuleKeyManager;

public class MyClientA {

    public static void main(String[] args) throws Exception {
        MyClientA clientA = new MyClientA();
        clientA.init();
        Thread.sleep(1000 * 1200);
    }

    public void init() {
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(AppleAction.class);

        // 模块信息
        int moduleId = ModuleKeyCont.ModuleA.moduleId;
        var moduleKey = ModuleKeyManager.me().getModuleKey(moduleId);

        // 注册模块(当前子服务器) 信息到网关
        ModuleMessage moduleMessage = createModuleMessage(moduleKey, barSkeleton);

        String ip = ExampleCont.ip;
        int port = ExampleCont.port;

        BoltClientServerSetting setting = new BoltClientServerSetting(ip, port)
                .setBarSkeleton(barSkeleton)
                .setModuleKey(moduleKey)
                .setModuleMessage(moduleMessage);

        BoltClientServer boltClientServer = setting.build();
        boltClientServer.init();
        boltClientServer.registerModuleToGate();
    }


    private static ModuleMessage createModuleMessage(ModuleKey moduleKey, BarSkeleton barSkeleton) {
        int[] cmdMergeArray = barSkeleton.getActionCommandManager().arrayCmdMerge();
        ModuleMessage moduleMessage = new ModuleMessage();
        moduleMessage.setModuleKey(moduleKey);
        moduleMessage.setName("游戏服 A");
        moduleMessage.setDescription("做大厅业务");
        moduleMessage.setCmdMergeArray(cmdMergeArray);

        return moduleMessage;
    }
}
