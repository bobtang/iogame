package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.action.DemoAction;
import org.junit.Test;

/**
 * @author 洛朱
 * @date 2021-12-24
 */
public class BarSkeletonBuilderTest {

    @Test
    public void build() {
        // 业务框架构建器
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        // 添加(请求响应)处理类. 用户可以定义自己的业务控制器
        builder
                .addActionController(DemoAction.class)
        ;

        // 构建业务框架
        BarSkeleton barSkeleton = builder.build();
    }
}