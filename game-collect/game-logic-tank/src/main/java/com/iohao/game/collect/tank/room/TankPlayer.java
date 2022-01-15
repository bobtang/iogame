package com.iohao.game.collect.tank.room;

import com.iohao.game.collect.common.room.AbstractPlayer;
import com.iohao.game.collect.proto.tank.TankBulletBox;
import lombok.Data;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * 坦克 玩家
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Data
public class TankPlayer extends AbstractPlayer {
    @Serial
    private static final long serialVersionUID = 774219456031784563L;

    /** 血条 */
    long hp;
    /** 坦克 所属队伍 */
    int team;
    /** 皮肤 */
    int skin;
    /** 速度 */
    int speed;

    /**
     * 坦克子弹
     * <pre>
     *     key : bulletType 子弹 类型
     *     value : TankBullet
     * </pre>
     */
    Map<Integer, TankBulletBox> tankBulletMap = new HashMap<>();


}
