package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.collect.common.room.AbstractRoom;
import com.iohao.game.collect.common.room.CreateRoomInfo;
import com.iohao.game.collect.common.room.flow.RoomCreateCustom;
import com.iohao.game.collect.tank.room.TankRoom;

/**
 * 坦克 房间创建
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class TankRoomCreateCustom implements RoomCreateCustom {

    static final TankRoom room = new TankRoom();

    @Override
    public AbstractRoom createRoom(CreateRoomInfo createRoomInfo) {
//        return new TankRoom();
        // TODO: 2022/1/14 开发阶段，只用一个房间
        return room;
    }
}
