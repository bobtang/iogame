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
public @interface DocActionSends {
    DocActionSend[] value();
}
