package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public interface ActionCont {
    interface Info {
        int cmd();

        default CmdInfo cmdInfo(int subCmd) {
            return CmdInfoFlyweightFactory.me().getCmdInfo(cmd(), subCmd);
        }
    }

    /**
     * 用户模块
     */
    interface UserModule extends Info {
        /**
         * 模块A - 主 cmd
         */
        int cmd = 1;

        Info info = () -> cmd;

        /** 登录验证 */
        int loginVerify = 1;
    }
}
