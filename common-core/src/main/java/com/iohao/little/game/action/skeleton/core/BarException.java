package com.iohao.little.game.action.skeleton.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarException extends Exception {
    int code;

    public BarException(String message, int code) {
        super(message);
        this.code = code;
    }

}
