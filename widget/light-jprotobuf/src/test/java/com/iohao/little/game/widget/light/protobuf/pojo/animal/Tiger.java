package com.iohao.little.game.widget.light.protobuf.pojo.animal;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import com.iohao.little.game.widget.light.protobuf.pojo.TempProtoFile;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 老虎
 *
 * @author 洛朱
 * @date 2022-01-25
 */
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = TempProtoFile.oneFileName, filePackage = TempProtoFile.oneFilePackage)
public class Tiger {
    /** id */
    int id;
    /** 食物 */
    Food food;
}
