package com.iohao.game.collect.user.action;

import com.iohao.game.collect.common.ActionCont;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
public interface UserCmd extends ActionCont.Info {
    /** 模块A - 主 cmd */
    int cmd = ActionCont.userModuleCmd;

    ActionCont.Info info = () -> cmd;

    /** 登录验证 */
    int loginVerify = 1;
}