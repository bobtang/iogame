package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import lombok.Setter;
import lombok.ToString;

/**
 * @author 洛朱
 * @date 2022-01-22
 */
@ToString
@Setter
@ProtobufClass(description = "")
@EnableZigZap
public class MyBird {
    /** id */
    @Protobuf(description = "id")
    public int id;
}
