package com.iohao.game.collect;

import com.iohao.game.collect.user.UserClientStartupConfig;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public class GameAll {
    public static void main(String[] args) {
        // 启动用户逻辑服
        UserClientStartupConfig userClientStartupConfig = new UserClientStartupConfig();
        userClientStartupConfig.startup();


    }
}
