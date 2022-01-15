package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import lombok.Setter;
import lombok.ToString;

/**
 * 坦克位置
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@Setter
@ProtobufClass(description = "坦克位置")
@EnableZigZap
public class TankMove {
    @Protobuf(description = "x 轴")
    public int x;

    @Protobuf(description = "y 轴")
    public int y;

    @Protobuf(description = "方向 H")
    public int directionH;
    @Protobuf(description = "方向 V")
    public int directionV;

    @Protobuf(description = "玩家 id")
    public long userId;

    @Protobuf(description = "hash")
    public int hash;
}
