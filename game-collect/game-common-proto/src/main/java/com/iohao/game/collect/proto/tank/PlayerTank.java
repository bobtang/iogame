package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
public class PlayerTank {
    /** id */
    @Protobuf(description = "玩家id")
    public long id;

    /** 血条 */
    @Protobuf(description = "坦克 血条")
    public long hp;

    @Protobuf(description = "坦克 所属队伍")
    public int team;

    @Protobuf(description = "坦克 速度")
    public int speed;

    @Protobuf(description = "坦克 皮肤")
    public int skin;

    @Protobuf(description = "坦克 子弹集 [key:子弹类型 ，value:子弹盒]")
    public List<TankBulletBox> tankBulletBoxList;

    @Protobuf(description = "坦克位置")
    public TankMove tankMove;

}
