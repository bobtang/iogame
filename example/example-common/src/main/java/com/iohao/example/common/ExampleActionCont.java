package com.iohao.example.common;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;

public interface ExampleActionCont {

    interface TheInfo {
        int cmd();

        default CmdInfo cmdInfo(int subCmd) {
            return CmdInfoFlyweightFactory.me().getCmdInfo(cmd(), subCmd);
        }
    }

//    /**
//     * 模块A
//     */
//    interface ExampleAppleCmd extends TheInfo {
//        /**
//         * 模块A - 主 cmd
//         */
//        int cmd = 2;
//
//        TheInfo info = () -> cmd;
//
//        int name = 0;
//        int age = 1;
//        /** jsr 303 测试 */
//        /** jsr 303 测试 */
//        int validate = 2;
//    }

//    interface ExampleBookCmd extends TheInfo {
//        int cmd = 3;
//        TheInfo info = () -> cmd;
//        int name = 0;
//        int get_apple_age = 1;
//        int message_queue = 2;
//
//        int broadcast_user_info = 3;
//    }

//    /** 用于推送 */
//    interface ExampleBroadcastCmd {
//        /** 推送模块 主cmd */
//        int cmd = 1;
//        TheInfo info = () -> cmd;
//        /** 用户账户信息 */
//        int user_account = 0;
//    }

}
