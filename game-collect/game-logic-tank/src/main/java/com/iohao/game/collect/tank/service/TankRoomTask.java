package com.iohao.game.collect.tank.service;

import com.iohao.game.collect.common.room.RoomService;
import com.iohao.game.collect.tank.room.TankRoom;

import java.util.Collection;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
public class TankRoomTask {

    RoomService roomService = RoomService.me();

    public void init() {

    }

    static class TaskRoomStart implements Runnable {
        RoomService roomService = RoomService.me();

        @Override
        public void run() {
            // 所有房间
            Collection<TankRoom> rooms = roomService.listRoom();



        }
    }
}
