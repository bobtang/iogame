package com.iohao.example.b;

import com.iohao.example.b.action.BookAction;
import com.iohao.example.common.ExampleCont;
import com.iohao.example.common.ModuleKeyCont;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.BoltClientServer;
import com.iohao.little.game.net.BoltClientServerSetting;
import com.iohao.little.game.net.common.ClientBarSkeleton;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleKey;
import com.iohao.little.game.net.message.common.ModuleKeyManager;

public class MyClientB {

    public static void main(String[] args) throws Exception {
        MyClientB clientA = new MyClientB();
        clientA.init();
        Thread.sleep(1000 * 1200);
    }

    public void init() {
        BarSkeleton barSkeleton = ClientBarSkeleton.newBarSkeleton(BookAction.class);

        // 模块信息
        int moduleId = ModuleKeyCont.ModuleB.moduleId;
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
        moduleMessage.setName("游戏服 B");
        moduleMessage.setDescription("五子棋");
        moduleMessage.setCmdMergeArray(cmdMergeArray);

        return moduleMessage;
    }
}
