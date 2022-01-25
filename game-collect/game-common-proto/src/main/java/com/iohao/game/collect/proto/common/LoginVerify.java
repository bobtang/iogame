package com.iohao.game.collect.proto.common;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 登录验证
 *
 * @author 洛朱
 * @date 2022-01-11
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.commonFileName, filePackage = GameProtoFile.commonFilePackage)
public class LoginVerify {
    /** jwt */
    public String jwt;
}
