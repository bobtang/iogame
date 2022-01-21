package com.iohao.little.game.action.skeleton.core.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * action 错误码
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ActionErrorEnum implements MsgExceptionInfo {
    
    ;

    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    ActionErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
