package com.iohao.little.game.action.skeleton.annotation;

import java.lang.annotation.*;

/**
 * @author 洛朱
 * @date 2022-01-31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DocActionSends {
    DocActionSend[] value();
}
