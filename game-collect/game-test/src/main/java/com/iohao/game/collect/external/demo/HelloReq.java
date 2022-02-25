package com.iohao.game.collect.external.demo;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author 洛朱
 * @date 2022-02-24
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
public class HelloReq {
    String name;
}
