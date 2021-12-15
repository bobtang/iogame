package com.iohao.example.common;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;

public interface ActionCont {

    interface TheInfo {
        int cmd();
        default CmdInfo cmdInfo(int subCmd) {
            return CmdInfoFlyweightFactory.me().getCmdInfo(cmd(), subCmd);
        }
    }

    /**
     * 模块A
     */
    interface AppleModule extends TheInfo {
        /**
         * 模块A - 主 cmd
         */
        int CMD = 2;

        TheInfo info = () -> CMD;

        int NAME = 0;
        int AGE = 1;
    }

    interface BookModule extends TheInfo {
        int CMD = 3;
        TheInfo info = () -> CMD;
        int NAME = 0;
        int GET_APPLE_AGE = 1;
    }

}
