package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.collect.common.room.flow.RoomPlayerCreateCustom;
import com.iohao.game.collect.tank.room.TankPlayerEntity;

/**
 * 坦克 创建玩家
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class TankRoomPlayerCreateCustom implements RoomPlayerCreateCustom {
    @Override
    @SuppressWarnings("unchecked")
    public TankPlayerEntity createPlayer() {
        TankPlayerEntity tankPlayerEntity = new TankPlayerEntity();
        tankPlayerEntity.setHp(10000);
        return tankPlayerEntity;
    }
}
