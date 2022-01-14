package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.collect.common.room.AbstractRoom;
import com.iohao.game.collect.common.room.flow.RoomGameStartCustom;

/**
 * 游戏开始
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class TankRoomGameStartCustom implements RoomGameStartCustom {
    @Override
    public boolean startBefore(AbstractRoom abstractRoom) {
        return false;
    }

    @Override
    public void startAfter(AbstractRoom abstractRoom) {

    }
}
