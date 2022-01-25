package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * 玩家的坦克
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
public class TankPlayer {
    /** 玩家id */
    long id;

    /** 坦克 血条 */
    long hp;

    /** 坦克 所属队伍 */
    int team;

    /** 坦克 速度 */
    int speed;

    /** 坦克 皮肤 */
    int skin;

    /** 坦克位置 */
    TankMove tankMove;

    /** 坦克子弹: key: 子弹 id 1 玩具弹, 2 雪弹; value : 数量 */
    Map<Integer, Integer> tankBulletMap;
}
