package com.iohao.game.collect.common.room;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

/**
 * 抽象玩家
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractPlayer implements Serializable {

    @Serial
    private static final long serialVersionUID = -26338708253909097L;
    /** userId 玩家 id */
    long userId;
    /** 用户所在位置 */
    int seat;
    /** true - 已准备 */
    boolean ready;
}
