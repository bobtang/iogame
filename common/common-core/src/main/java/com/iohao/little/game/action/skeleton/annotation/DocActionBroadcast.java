package com.iohao.little.game.action.skeleton.annotation;

import java.lang.annotation.*;

/**
 * 文档： 广播相关
 * <pre>
 *     这个注解不是必须的
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DocActionBroadcast {
    Class<?> value() default Object.class;
}
