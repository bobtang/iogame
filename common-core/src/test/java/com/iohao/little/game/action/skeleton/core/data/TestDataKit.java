package com.iohao.little.game.action.skeleton.core.data;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.core.ActionCommandHandler;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.DefaultActionCommandFlowExecute;
import com.iohao.little.game.action.skeleton.core.action.BeeAction;
import com.iohao.little.game.action.skeleton.core.action.BirdAction;
import com.iohao.little.game.action.skeleton.core.flow.interal.ExecuteTimeInOut;
import com.iohao.little.game.common.kit.ClassScanner;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Predicate;

@UtilityClass
public class TestDataKit {

    public BarSkeleton newBarSkeleton() {
        return createBuilder().build();
    }

    public BarSkeletonBuilder createBuilder() {
        // 尽量做到所有操作是可插拔的. 详细配置 see BarSkeletonBuilder.build
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.getBarSkeletonSetting().setPrintActionShort(true);

        // 添加(请求响应)处理类. 用户可以定义自己的业务控制器 - 这里推荐实现扫描包的形式添加 tcp 处理类
//        builder
//                .addActionController(BeeAction.class)
//                .addActionController(BirdAction.class)
//        ;

        Predicate<Class<?>> predicateFilter = (clazz) -> {
            ActionController annotation = clazz.getAnnotation(ActionController.class);
            return annotation != null;
        };

        String packagePath = BeeAction.class.getPackageName();
        ClassScanner classScanner = new ClassScanner(packagePath, predicateFilter);
        List<Class<?>> classList = classScanner.listScan();
        classList.forEach(builder::addActionController);

        return builder;
    }
}
