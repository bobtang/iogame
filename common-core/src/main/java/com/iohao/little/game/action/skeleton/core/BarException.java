package com.iohao.little.game.action.skeleton.core;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务框架异常
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Getter
@Setter
public class BarException extends Exception {
    int code;

    public BarException(String message, int code) {
        super(message);
        this.code = code;
    }

}
