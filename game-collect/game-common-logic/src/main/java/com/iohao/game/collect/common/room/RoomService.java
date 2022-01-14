package com.iohao.game.collect.common.room;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
     *     key : playerId
     *     value : roomId
     * </pre>
     */
    final Map<Long, String> playerRoomMap = new HashMap<>();


}
