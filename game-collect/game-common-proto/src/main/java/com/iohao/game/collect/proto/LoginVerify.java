package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
@Data
@ProtobufClass(description = "登录验证")
@EnableZigZap
public class LoginVerify {
    /** name */
    @Protobuf(description = "jwt")
    String jwt;
}
