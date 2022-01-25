package com.iohao.little.game.widget.light.protobuf.pojo.animal;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 食物
 *
 * @author 洛朱
 * @date 2022-01-25
 */
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = "one.proto", filePackage = "pb.one")
public class Food {
    /** id */
    int id;
    /** 食物名 */
    String foodName;
}
