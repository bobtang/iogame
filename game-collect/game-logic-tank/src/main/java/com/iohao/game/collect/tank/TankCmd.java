package com.iohao.game.collect.tank;

import com.iohao.game.collect.common.ActionModuleCmd;

/**
 * 游戏 - 坦克模块
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface TankCmd extends ActionModuleCmd.Info {
    /** 模块 - 主 cmd : 2 */
    int cmd = ActionModuleCmd.tankModuleCmd;

    ActionModuleCmd.Info info = () -> cmd;

    /** 创建房间 */
    int createRoom = 1;
    /** 进入房间 */
    int enterRoom = 2;
    /** 游戏开始 */
    int gameStart = 3;
    /** 子弹配置 */
    int getTankBulletConfigRes = 4;
    /** 坦克移动 */
    int tankMove = 5;
    /** 坦克射击(发射子弹) */
    int shooting = 6;
    /** 子弹消失（死亡） */
    int bulletDead = 7;
    /** 坦克受伤 */
    int hurt = 8;
}