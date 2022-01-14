package com.iohao.game.collect.tank.action;

import com.iohao.game.collect.common.ActionCont;

/**
 * 游戏 - 坦克模块
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface TankCmd extends ActionCont.Info {
    /** 模块A - 主 cmd */
    int cmd = ActionCont.tankModuleCmd;

    ActionCont.Info info = () -> cmd;

    /** 创建房间 */
    int createRoom = 1;
    /** 进入房间 */
    int enterRoom = 2;
    /** 游戏开始 */
    int gameStart = 3;
}