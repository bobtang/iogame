package com.iohao.game.collect.common.room;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 房间的管理
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomService {
    /**
     * 房间 map
     * <pre>
     *     key : roomId
     *     value : room
     * </pre>
     */
    final Map<String, AbstractRoom> roomMap = new HashMap<>();

    /**
     * 玩家对应的房间 map
     * <pre>
     *     key : userId
     *     value : roomId
     * </pre>
     */
    final Map<Long, String> userRoomMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends AbstractRoom> T getRoomByUserId(long userId) {
        // 通过 userId 得到 roomId
        String roomId = userRoomMap.get(userId);

        if (Objects.isNull(roomId)) {
            return null;
        }

        // 通过 roomId 得到 room
        return getRoom(roomId);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractRoom> T getRoom(String roomId) {
        return (T) this.roomMap.get(roomId);
    }

    public void addRoom(AbstractRoom room) {
        String roomId = room.getRoomId();
        this.roomMap.put(roomId, room);
    }

    public void addPlayer(AbstractRoom room, AbstractPlayer player) {
        room.addPlayer(player);
        this.userRoomMap.put(player.getId(), room.getRoomId());
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractRoom> Collection<T> listRoom() {
        return (Collection<T>) this.roomMap.values();
    }

    public static RoomService me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final RoomService ME = new RoomService();
    }

}
