package com.iohao.game.collect.common.room;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
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
     *     key is playerId
     *     value is player
     * </pre>
     */
    final Map<Long, AbstractPlayer> playerMap = new HashMap<>();

    /**
     * 玩家位置
     * <pre>
     *     key is seat
     *     value is playerId
     * </pre>
     */
    final Map<Integer, Long> playerSeatPlayerIdMap = new TreeMap<>();

    /** 房间唯一 id - uuid */
    String roomId;
    /** 房间号 */
    int roomNo;

    /** 创建房间信息 */
    CreateRoomInfo createRoomInfo;
    /** 房间空间大小: 4 就是4个人上限 (根据规则设置) */
    int spaceSize;

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

    public Collection<Long> listPlayerId() {
        return this.playerMap.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayer> T getPlayerById(long playerId) {
        return (T) this.playerMap.get(playerId);
    }

    /**
     * 添加玩家到房间内
     *
     * @param player 玩家
     */
    public void addPlayer(AbstractPlayer player) {
        long playerId = player.getId();
        this.playerMap.put(playerId, player);
        this.playerSeatPlayerIdMap.put(player.getSeat(), playerId);
    }
}
