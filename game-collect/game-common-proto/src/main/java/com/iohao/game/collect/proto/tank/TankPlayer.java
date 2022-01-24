package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 玩家的坦克
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@Setter
@ProtobufClass(description = "玩家的坦克")
@EnableZigZap
public class TankPlayer {
    @Protobuf(description = "玩家id")
    public long id;

    @Protobuf(description = "坦克 血条")
    public long hp;

    @Protobuf(description = "坦克 所属队伍")
    public int team;

    @Protobuf(description = "坦克 速度")
    public int speed;

    @Protobuf(description = "坦克 皮肤")
    public int skin;

    @Protobuf(description = "坦克位置")
    public TankMove tankMove;

    @Protobuf(description = "坦克子弹: key: 子弹 id 1 玩具弹, 2 雪弹; value : 数量")
    public Map<Integer, Integer> tankBulletMap;
}
