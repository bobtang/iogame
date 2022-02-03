package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 坦克受伤
 *
 * @author 洛朱
 * @date 2022-02-02
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.tankFileName, filePackage = GameProtoFile.tankFilePackage)
public class TankHurt {
    /** 坦克子弹 */
    int tankBulletId;

    /** 被攻击的玩家 id */
    int targetUserId;
}
