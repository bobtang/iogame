package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import lombok.Setter;
import lombok.ToString;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@Setter
@ProtobufClass(description = "坦克子弹")
@EnableZigZap
public class TankBullet {
    /** id */
    @Protobuf(description = "id")
    public long id;

    @Protobuf(description = "子弹 类型2")
    public BulletEnum bulletEnum;

}
