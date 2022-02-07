package com.iohao.game.collect.common.room;

import com.iohao.game.collect.common.send.AbstractFlowContextSend;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

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
    final Map<Long, AbstractPlayer> playerMap = new NonBlockingHashMap<>();

    /**
     * 玩家位置
     * <pre>
     *     key is seat
     *     value is userId
     * </pre>
     */
    final Map<Integer, Long> playerSeatMap = new TreeMap<>();

    /** 房间唯一 id */
    long roomId;
    /** 房间号 */
    int roomNo;

    /** 创建房间信息 */
    CreateRoomInfo createRoomInfo;
    /** 房间空间大小: 4 就是4个人上限 (根据规则设置) */
    int spaceSize;

    /** 房间状态 */
    RoomStatusEnum roomStatusEnum = RoomStatusEnum.wait;

    protected abstract <T extends AbstractFlowContextSend> T createSend(FlowContext flowContext);

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
        return listPlayer().stream()
                .filter(predicate)
                .toList();
    }

    public Collection<Long> listPlayerId(long excludePlayerId) {
        return listPlayerId().stream()
                .filter(playerId -> playerId != excludePlayerId)
                .toList();
    }

    public Collection<Long> listPlayerId() {
        return this.playerMap.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPlayer> T getPlayerById(long userId) {
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
        long userId = player.getUserId();
        this.playerMap.put(userId, player);
        this.playerSeatMap.put(player.getSeat(), userId);
    }

    public boolean isStatus(RoomStatusEnum roomStatusEnum) {
        return this.roomStatusEnum == roomStatusEnum;
    }

    /**
     * 广播业务数据给房间内的所有玩家
     *
     * @param flowContext  flow 上下文
     * @param methodResult 广播的业务数据
     */
    public void broadcast(FlowContext flowContext, Object methodResult) {
        this.broadcast(flowContext, methodResult, this.listPlayerId());
    }

    /**
     * 广播业务数据给用户列表
     *
     * @param flowContext  flow 上下文
     * @param methodResult 广播的业务数据
     * @param userIdList   用户列表
     */
    public void broadcast(FlowContext flowContext, Object methodResult, Collection<Long> userIdList) {
        this.broadcast(flowContext, methodResult, userIdList, 0);
    }

    /**
     * 广播业务数据给房间内的所有玩家， 排除指定用户
     *
     * @param flowContext   flow 上下文
     * @param methodResult  广播的业务数据
     * @param excludeUserId 排除的用户
     */
    public void broadcast(FlowContext flowContext, Object methodResult, long excludeUserId) {
        this.broadcast(flowContext, methodResult, this.listPlayerId(), excludeUserId);
    }

    /**
     * 广播业务数据给用户列表, 并排除一个用户
     *
     * @param flowContext   flow 上下文
     * @param methodResult  广播的业务数据
     * @param userIdList    用户列表
     * @param excludeUserId 排除的用户
     */
    public void broadcast(FlowContext flowContext, Object methodResult, Collection<Long> userIdList, long excludeUserId) {
        flowContext.setMethodResult(methodResult);

        AbstractFlowContextSend send = this.createSend(flowContext)
                .addUserId(userIdList, excludeUserId);

        send.send();
    }

    /**
     * 广播业务数据给指定的用户
     *
     * @param flowContext  flow 上下文
     * @param methodResult 广播的业务数据
     * @param userId       指定的用户
     */
    public void broadcastUser(FlowContext flowContext, Object methodResult, long userId) {
        flowContext.setMethodResult(methodResult);

        AbstractFlowContextSend send = this.createSend(flowContext)
                .addUserId(userId);

        send.send();
    }
}
