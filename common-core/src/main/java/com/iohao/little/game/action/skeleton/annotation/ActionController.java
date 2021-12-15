package com.iohao.little.game.action.skeleton.annotation;

import java.lang.annotation.*;

/**
 * 控制器, 一般用作类的路由
 * <BR>
 *
 * @author 洛朱
 * @date 2021/12/12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionController {
    /**
     * @return 路由
     */
    int value();
}
