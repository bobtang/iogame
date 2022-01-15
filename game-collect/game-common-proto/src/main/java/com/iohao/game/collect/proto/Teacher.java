package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufIDLGenerator;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.IOException;

/**
 * ssssl
 *
 * @author 洛朱
 * @date 2022-01-07
 */
@ToString
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtobufClass(description = "老师")
@EnableZigZap
public class Teacher {
    /** name */
    @Protobuf(description = "姓名")
    String name;
    int id;
    long age;
    @Protobuf(description = "邮箱")
    String email;

    Double doubleF;
    Float floatF;
    byte[] bytesF;

    Boolean boolF;

    Object student;

    public static void main(String[] args) {
        Codec<Teacher> teacherCodec = ProtobufProxy.create(Teacher.class);

        Teacher teacher = new Teacher();
        teacher.id = 1;
        teacher.name = "alex";


        try {
            byte[] encode = teacherCodec.encode(teacher);

            Teacher teacherParse = teacherCodec.decode(encode);

            System.out.println(teacherParse);

            String code = ProtobufIDLGenerator.getIDL(Teacher.class);

            System.out.println(code);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
