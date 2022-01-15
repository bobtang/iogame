package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@ProtobufClass(description = "坦克子弹")
@EnableZigZap
public class TankBulletBox {
    @Protobuf(description = "子弹 类型")
    public BulletEnum bulletEnum;

    /** id */
    @Protobuf(description = "数量")
    public int amount;
}