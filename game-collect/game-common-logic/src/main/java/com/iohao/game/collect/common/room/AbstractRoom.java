package com.iohao.game.collect.common.room;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 抽象房间
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = -6937915481102847959L;

    /**
     * 玩家
     * <pre>
     *     key is userId
     *     value is player
     * </pre>
     */
    final ConcurrentHashMap<Long, AbstractPlayer> playerMap = new ConcurrentHashMap<>();

    /**
     * 玩家位置
     * <pre>
     *     key is seat
     *     value is userId
     * </pre>
     */
    final Map<Integer, Long> playerSeatMap = new TreeMap<>();

    /** 房间唯一 id - uuid */
    String roomId;
    /** 房间号 */
    int roomNo;

    /** 创建房间信息 */
    CreateRoomInfo createRoomInfo;
    /** 房间空间大小: 4 就是4个人上限 (根据规则设置) */
    int spaceSize;

    /** 房间状态 */
    RoomStatusEnum roomStatusEnum = RoomStatusEnum.wait;

    /**
     * 玩家列表: 所有玩家信息
     *
     * @param <T> 玩家
     * @return 所有玩家信息 (包括退出房间的玩家信息)
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayer> Collection<T> listPlayer() {
        return (Collection<T>) this.playerMap.values();
    }

    public List<AbstractPlayer> listPlayer(Predicate<AbstractPlayer> predicate) {
        return listPlayer().stream().filter(predicate).collect(Collectors.toList());
    }

    public Collection<Long> listUserId() {
        return this.playerMap.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayer> T getUserById(long userId) {
        return (T) this.playerMap.get(userId);
    }

    public boolean existUser(long userId) {
        return this.playerMap.get(userId) != null;
    }

    /**
     * 添加玩家到房间内
     *
     * @param player 玩家
     */
    public void addPlayer(AbstractPlayer player) {
        long userId = player.getId();
        this.playerMap.put(userId, player);
        this.playerSeatMap.put(player.getSeat(), userId);
    }

    public boolean isStatus(RoomStatusEnum roomStatusEnum) {
        return this.roomStatusEnum == roomStatusEnum;
    }
}
