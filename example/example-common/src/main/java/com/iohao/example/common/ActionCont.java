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
        int cmd = 2;

        TheInfo info = () -> cmd;

        int name = 0;
        int age = 1;
    }

    interface BookModule extends TheInfo {
        int cmd = 3;
        TheInfo info = () -> cmd;
        int name = 0;
        int get_apple_age = 1;
        int message_queue = 2;
    }

}
