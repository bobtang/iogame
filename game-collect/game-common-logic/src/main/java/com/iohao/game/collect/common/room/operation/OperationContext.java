package com.iohao.game.collect.common.room.operation;

import com.iohao.game.collect.common.room.AbstractPlayer;
import com.iohao.game.collect.common.room.AbstractRoom;

/**
 * 操作上下文
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class OperationContext {
    /** 操作类型 */
    int operation;

    AbstractRoom room;

    AbstractPlayer player;

}
