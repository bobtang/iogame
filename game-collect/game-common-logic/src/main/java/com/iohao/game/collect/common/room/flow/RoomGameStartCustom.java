package com.iohao.game.collect.common.room.flow;

import com.iohao.game.collect.common.room.AbstractRoom;

/**
 * 游戏开始
 * 玩家全都准备后会触发
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface RoomGameStartCustom {
    /**
     * 游戏开始前的逻辑校验
     *
     * <pre>
     *     方法解说:
     *     比如做一个游戏, 8人场, 由于人数需要很多.
     *     假设规则定义为满足4人准备, 就可以开始游戏.
     *     那么这个开始前就可以派上用场了, 毕竟你永远不知道子游戏的规则是什么.
     *     所以最好预留一个这样的验证接口, 交给子类游戏来定义开始游戏的规则
     * </pre>
     *
     * @param abstractRoom 房间
     * @return 返回 true, 会执行 {@link RoomGameStartCustom#startAfter}. 并更新用户的状态为战斗状态
     */
    boolean startBefore(AbstractRoom abstractRoom);

    /**
     * 游戏开始前的after逻辑. 这里可以游戏正式开始的逻辑
     * <pre>
     *     可以发牌
     * </pre>
     *
     * @param abstractRoom 房间
     */
    void startAfter(AbstractRoom abstractRoom);
}
