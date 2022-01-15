package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

import java.util.List;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@ToString
@ProtobufClass(description = "坦克子弹配置 响应")
@EnableZigZap
public class TankBulletConfigRes {
    /** id */
    @Protobuf(description = "id")
    public List<TankBulletConfig> tankBulletConfigs;
}
