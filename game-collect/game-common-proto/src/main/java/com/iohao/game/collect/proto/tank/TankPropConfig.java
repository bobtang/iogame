package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

/**
 * 坦克道具配置
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@ProtobufClass(description = "坦克道具配置")
@EnableZigZap
public class TankPropConfig {
    /** id */
    @Protobuf(description = "id")
    public int id;

    @Protobuf(description = "道具名")
    public String name;
}
