package com.iohao.game.collect;

import com.iohao.game.collect.hall.HallClientStartupConfig;
import com.iohao.game.collect.tank.TankClientStartupConfig;
import com.iohao.little.game.net.client.core.ClientStartupConfig;

import java.util.List;

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
        listLogicServer().forEach(ClientStartupConfig::startup);
    }

    public List<ClientStartupConfig> listLogicServer() {
        return List.of(
                // 大厅
                new HallClientStartupConfig(),
                // 坦克游戏
                new TankClientStartupConfig()
        );
    }
}
