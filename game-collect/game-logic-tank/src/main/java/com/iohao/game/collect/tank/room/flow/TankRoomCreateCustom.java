package com.iohao.game.collect.tank.room.flow;

import cn.hutool.core.util.IdUtil;
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

    @Override
    @SuppressWarnings("unchecked")
    public AbstractRoom createRoom(CreateRoomInfo createRoomInfo) {
        String roomId = IdUtil.simpleUUID();

        TankRoom room = new TankRoom();
        room.setRoomId(roomId);

        return room;
    }
}
