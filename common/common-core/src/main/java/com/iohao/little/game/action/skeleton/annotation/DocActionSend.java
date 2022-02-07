package com.iohao.little.game.action.skeleton.annotation;

import java.lang.annotation.*;

/**
 * 文档相关
 * <pre>
 *     仅用于推送文档生成
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(DocActionSends.class)
public @interface DocActionSend {
    /** 主路由 */
    int cmd();

    /** 子路由 */
    int subCmd();

    /** 业务对象class */
    Class<?> dataClass();

    /** 推送描述 */
    String description() default "";
}
