package com.iohao.little.game.net.external.bootstrap.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public abstract class ExternalMessage {
    /** 处理多种协议的请求: 0 pb */
    byte protocolCode;
    /** 请求命令类型: 0 心跳，1 业务 */
    short cmdCode;
    /** 业务路由（高16为主, 低16为子） */
    int mergeCmd;
    /** 协议开关，用于一些协议级别的开关控制，比如 安全校验等 : 0 不做任何处理 */
    byte protocolSwitch;
    /** 业务请求体长度 */
    int contentLength = 0;
    /** 业务请求体 */
    byte[] content;


    public void setContent(byte[] content) {
        if (content != null) {
            this.content = content;
            this.contentLength = content.length;
        }
    }
}
