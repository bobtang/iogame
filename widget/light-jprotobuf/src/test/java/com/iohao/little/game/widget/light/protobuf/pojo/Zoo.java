package com.iohao.little.game.widget.light.protobuf.pojo;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import com.iohao.little.game.widget.light.protobuf.pojo.animal.Cat;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 动物园
 *
 * @author 洛朱
 * @date 2022-01-25
 */
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = TempProtoFile.commonFileName, filePackage = TempProtoFile.commonFilePackage)
public class Zoo {
    /** id */
    long id;
    /** 动物园名 */
    String zooName;
    /** 小猫 */
    Cat cat;
}
