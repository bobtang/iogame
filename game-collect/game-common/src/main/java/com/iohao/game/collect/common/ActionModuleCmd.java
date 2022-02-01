package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public interface ActionModuleCmd {
    interface Info {
        int cmd();

        default CmdInfo cmdInfo(int subCmd) {
            return CmdInfoFlyweightFactory.me().getCmdInfo(cmd(), subCmd);
        }
    }

    /** 用户模块 */
    int userModuleCmd = 1;
    /** 游戏 坦克模块 */
    int tankModuleCmd = 2;

}
