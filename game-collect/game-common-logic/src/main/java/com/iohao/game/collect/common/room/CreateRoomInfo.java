package com.iohao.game.collect.common.room;

import lombok.Data;

/**
 * 创建房间信息
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Data
public class CreateRoomInfo {

    /** 玩法规则信息 - (创建房间时添加) */
    RuleInfo ruleInfo;
    /** 游戏 id */
    long gameId;
    /** 房间可供几个人玩 */
    int spaceSize;
    /** 创建的玩家 id */
    int createPlayerId;
}
