package com.iohao.game.collect.common.room;

import com.iohao.game.collect.common.room.flow.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * 游戏流
 * <pre>
 *     游戏规则
 *     创建玩家
 *
 *     房间创建
 *
 *     进入房间
 *     游戏开始
 *
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameFlowService {
    /** 游戏规则 */
    RoomRuleInfoCustom roomRuleInfoCustom;
    /** 游戏开始 */
    RoomGameStartCustom roomGameStartCustom;
    /** 创建玩家 */
    RoomPlayerCreateCustom roomPlayerCreateCustom;
    /** 房间创建 */
    RoomCreateCustom roomCreateCustom;
    /** 进入房间 */
    RoomEnterCustom roomEnterCustom;


}
