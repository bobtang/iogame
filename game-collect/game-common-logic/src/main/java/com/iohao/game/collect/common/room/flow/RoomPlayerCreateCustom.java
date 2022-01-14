package com.iohao.game.collect.common.room.flow;

import com.iohao.game.collect.common.room.AbstractPlayer;

/**
 * 创建玩家 - 自定义
 * <pre>
 *     延迟到子游戏中实现, 以便适应不同的子游戏规则
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface RoomPlayerCreateCustom {
    /**
     * 构建房间内的玩家
     *
     * @return 玩家
     */
    AbstractPlayer createPlayer();
}
