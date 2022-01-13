package com.iohao.example.client;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.little.game.common.kit.ClassScanner;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 客户端业务框架
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@UtilityClass
public class ExampleClientBarSkeleton {
    public BarSkeleton newBarSkeleton(Class<?>... actionClazz) {
        return createBuilder(actionClazz).build();
    }

    public BarSkeletonBuilder createBuilder(Class<?>... actionClazzArray) {
        // 尽量做到所有操作是可插拔的.
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.addInOut(new DebugInOut());
        // 添加(请求响应)处理类. 用户可以定义自己的业务控制器 - 这里推荐实现扫描包的形式添加 tcp 处理类
//        builder
//                .addActionController(DogAction.class)
        ;

        final Predicate<Class<?>> predicateFilter = (clazz) -> {
            ActionController annotation = clazz.getAnnotation(ActionController.class);
            return Objects.nonNull(annotation);
        };

        for (Class<?> actionClazz : actionClazzArray) {
            String packagePath = actionClazz.getPackageName();
            ClassScanner classScanner = new ClassScanner(packagePath, predicateFilter);
            List<Class<?>> classList = classScanner.listScan();
            classList.forEach(builder::addActionController);
        }


        return builder;
    }
}