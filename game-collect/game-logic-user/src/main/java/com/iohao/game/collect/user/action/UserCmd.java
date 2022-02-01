package com.iohao.game.collect.user.action;

import com.iohao.game.collect.common.ActionModuleCmd;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
public interface UserCmd extends ActionModuleCmd.Info {
    /** 模块A - 主 cmd */
    int cmd = ActionModuleCmd.userModuleCmd;

    ActionModuleCmd.Info info = () -> cmd;

    /** 登录验证 */
    int loginVerify = 1;
}