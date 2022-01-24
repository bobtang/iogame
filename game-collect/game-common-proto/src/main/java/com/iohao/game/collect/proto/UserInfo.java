package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
@ToString
@ProtobufClass(description = "用户信息")
@EnableZigZap
public class UserInfo implements Serializable {
    @Serial
    @Ignore
    private static final long serialVersionUID = 5076168569348502895L;

    /** id */
    @Protobuf(description = "id")
    public long id;

    @Protobuf(description = "用户名")
    public String name;
}
