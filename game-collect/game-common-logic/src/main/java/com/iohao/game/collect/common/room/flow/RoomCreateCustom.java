package com.iohao.game.collect.common.room.flow;

import com.iohao.game.collect.common.room.AbstractRoom;
import com.iohao.game.collect.common.room.CreateRoomInfo;

/**
 * 房间创建 - 自定义
 * <pre>
 *     延迟到子游戏中实现, 以便适应不同的子游戏规则
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface RoomCreateCustom {
    /**
     * 创建房间, 子类只需要关心 ruleInfo (房间配置, 规则信息)
     * <p>
     * 根据 创建游戏规则
     *
     * @param createRoomInfo 创建房间信息
     * @param <T>            AbstractRoom
     * @return 房间
     */
    <T extends AbstractRoom> T createRoom(CreateRoomInfo createRoomInfo);
}
