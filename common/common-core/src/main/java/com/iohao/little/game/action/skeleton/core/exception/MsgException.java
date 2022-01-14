package com.iohao.little.game.action.skeleton.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 业务框架 异常消息
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Getter
@Setter
public class MsgException extends Exception {
    @Serial
    private static final long serialVersionUID = -4977523514509693190L;

    /** 异常消息码 */
    final int msgCode;

    public MsgException(int msgCode, String message) {
        super(message);
        this.msgCode = msgCode;
    }

}
