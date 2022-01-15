package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

/**
 * 子弹类型
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@ProtobufClass(description = "子弹类型")
@EnableZigZap
public enum BulletEnum {
    @Protobuf(description = "玩具弹")
    toyBullet,
    @Protobuf(description = "方向弹")
    directionBullet,
    @Protobuf(description = "雪弹")
    snowBullet,
}