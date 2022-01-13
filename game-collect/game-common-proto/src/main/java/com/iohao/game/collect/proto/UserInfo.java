package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
@Data
@ProtobufClass(description = "用户信息")
@EnableZigZap
public class UserInfo {
    /** id */
    @Protobuf(description = "id")
    long id;

    String name;
}
