package com.iohao.game.collect;

import com.iohao.game.collect.hall.HallClientStartupConfig;
import com.iohao.game.collect.tank.TankClientStartupConfig;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameLogicAll {
    public static void main(String[] args) {
        GameLogicAll gameLogicAll = new GameLogicAll();
        gameLogicAll.init();
    }

    public void init() {
        // 启动用户逻辑服

        // 大厅
        HallClientStartupConfig hallClientStartupConfig = new HallClientStartupConfig();
        hallClientStartupConfig.startup();

        // 坦克游戏
        TankClientStartupConfig tankClientStartupConfig = new TankClientStartupConfig();
        tankClientStartupConfig.startup();
    }
}
