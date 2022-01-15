package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

/**
 * 坦克子弹配置
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@ProtobufClass(description = "坦克子弹配置")
@EnableZigZap
public class TankBulletConfig {
    @Protobuf(description = "子弹 类型2")
    public BulletEnum bulletEnum;

    @Protobuf(description = "子弹 攻击力")
    public long attackValue;

    @Protobuf(description = "子弹 速度")
    public int speed;
}