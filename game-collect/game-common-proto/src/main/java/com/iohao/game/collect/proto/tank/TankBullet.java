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
 * 坦克子弹
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.tankFileName, filePackage = GameProtoFile.tankFilePackage)
public class TankBullet {
    /** id */
    int id;
    /** 子弹的位置 */
    TankLocation tankLocation;
    /** 所属玩家 */
    long playerId;
    /** 射击id (由服务器生成的唯一id) */
    long shootId;
}
