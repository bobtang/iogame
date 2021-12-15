package com.iohao.little.game.action.skeleton.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *     方法 subCmd 注解
 *     一般用作类方法的路由
 * </pre>
 *
 * @author 洛朱
 * @date 2021/12/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionMethod {
    /**
     * subCmd int
     *
     * @return subCmd
     */
    int value();
}
