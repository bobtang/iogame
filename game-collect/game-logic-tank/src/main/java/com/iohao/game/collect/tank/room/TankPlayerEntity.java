package com.iohao.game.collect.tank.room;

import com.iohao.game.collect.common.room.AbstractPlayer;
import com.iohao.game.collect.proto.tank.TankLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;

/**
 * 坦克 玩家
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TankPlayerEntity extends AbstractPlayer {
    @Serial
    private static final long serialVersionUID = 774219456031784563L;

    /** 血条 */
    long hp;
    /** 坦克 所属队伍 */
    int team;
    /** 速度 */
    int speed;
    /** 皮肤 */
    int skin;

    TankLocation tankLocation = new TankLocation();

    /** 房间 id */
    long roomId;
}
