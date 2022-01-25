package com.iohao.game.collect.proto.common;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.commonFileName, filePackage = GameProtoFile.commonFilePackage)
public class UserInfo implements Serializable {
    @Serial
    @Ignore
    private static final long serialVersionUID = 5076168569348502895L;

    /** id */
    long id;
    /** 用户名 */
    String name;
}
