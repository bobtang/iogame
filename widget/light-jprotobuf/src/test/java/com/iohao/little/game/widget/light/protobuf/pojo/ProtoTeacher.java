package com.iohao.little.game.widget.light.protobuf.pojo;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * teacher
 *
 * @author 洛朱
 * @date 2022-01-07
 */
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = TempProtoFile.commonFileName, filePackage = TempProtoFile.commonFilePackage)
public class ProtoTeacher {
    /** 姓名 */
    String name;
    int id;
    long age;
    /** 邮箱 */
    String email;

    Double doubleF;
    Float floatF;
    byte[] bytesF;

    Boolean boolF;

//    Object student;

}
