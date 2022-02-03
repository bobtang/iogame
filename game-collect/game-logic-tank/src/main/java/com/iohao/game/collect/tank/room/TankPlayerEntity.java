package com.iohao.game.collect.tank.room;

import com.iohao.game.collect.common.exception.GameCodeEnum;
import com.iohao.game.collect.common.room.AbstractPlayer;
import com.iohao.game.collect.proto.tank.TankBullet;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashMap;

import java.io.Serial;
import java.util.Map;

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

    /**
     * 坦克子弹
     * <pre>
     *     key : 子弹 id 1 玩具弹, 2 雪弹
     *     value : 数量
     * </pre>
     */
    Map<Integer, Integer> tankBulletMap = new NonBlockingHashMap<>();

    /** 房间 id */
    long roomId;

    public void shooting(TankBullet tankBullet) throws MsgException {
        int bulletId = tankBullet.id;

        tankBullet.tankLocation.playerId = this.userId;
        tankBullet.playerId = this.userId;

        this.removeBullet(bulletId);
    }

    private void removeBullet(int bulletId) throws MsgException {
        removeBullet(bulletId, 1);
    }

    private void removeBullet(int bulletId, int counter) throws MsgException {
        Integer bulletCounter = tankBulletMap.get(bulletId);

        boolean result = bulletCounter == null || bulletCounter >= counter;

        GameCodeEnum.tankBulletDeficiency.assertFalse(result);

        bulletCounter -= counter;

        tankBulletMap.put(bulletId, bulletCounter);
    }

}
