package com.iohao.game.collect.common.room.flow;

import com.iohao.game.collect.common.room.AbstractRoom;

/**
 * 进入房间 (重连)
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface RoomEnterCustom {

    /**
     * 进入房间
     *
     * @param playerId      玩家 id
     * @param abstractRoom  玩家所在房间
     * @param roomEnterInfo 进入房间请求信息
     * @return enter Response
     */
    RoomEnterInfo enterRoom(long playerId, AbstractRoom abstractRoom, RoomEnterInfo roomEnterInfo);

}
