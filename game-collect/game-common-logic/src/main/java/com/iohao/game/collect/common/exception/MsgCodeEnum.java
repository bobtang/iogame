package com.iohao.game.collect.common.exception;

import com.iohao.little.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum MsgCodeEnum implements MsgExceptionInfo {
    CLASS_NOT_EXIST(-1, "");

    /** 消息码 */
    final int code;
    /** 消息模板 */
    final String msg;

    MsgCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
