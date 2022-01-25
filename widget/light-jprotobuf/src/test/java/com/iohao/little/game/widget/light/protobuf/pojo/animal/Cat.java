package com.iohao.little.game.widget.light.protobuf.pojo.animal;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import com.iohao.little.game.widget.light.protobuf.pojo.TempProtoFile;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * 猫
 *
 * @author 洛朱
 * @date 2022-01-25
 */
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = TempProtoFile.oneFileName, filePackage = TempProtoFile.oneFilePackage)
public class Cat {
    /** id */
    int id;

    /** 猫的名字 */
    String catName;
    /**
     * 食物 map
     */
    Map<Integer, Integer> foodMap;
}
