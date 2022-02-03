package com.iohao.game.collect.hall;

import com.iohao.game.collect.common.ActionModuleCmd;

/**
 * @author 洛朱
 * @date 2022-02-02
 */
public interface HallCmd extends ActionModuleCmd.Info {
    /** 模块A - 主 cmd */
    int cmd = ActionModuleCmd.hallModuleCmd;

    ActionModuleCmd.Info info = () -> cmd;

    /** 登录验证 */
    int loginVerify = 1;

}
