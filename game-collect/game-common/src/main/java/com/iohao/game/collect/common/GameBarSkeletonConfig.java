package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonSetting;
import com.iohao.little.game.action.skeleton.core.ParseType;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.little.game.common.kit.ClassScanner;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 实践案例游戏的 业务框架 配置
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@UtilityClass
public class GameBarSkeletonConfig {

    public BarSkeletonBuilder createBuilder(Class<?>... actionClazzArray) {
        // 尽量做到所有操作是可插拔的.
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.addInOut(new DebugInOut());

        // 使用 pb
        BarSkeletonSetting setting = builder.getSetting();
        setting.setParseType(ParseType.PB);

        // 过滤条件：只解析带有 ActionController 注解的 class
        final Predicate<Class<?>> predicateFilter = (clazz) -> Objects.nonNull(clazz.getAnnotation(ActionController.class));


        for (Class<?> actionClazz : actionClazzArray) {

            // 扫描
            String packagePath = actionClazz.getPackageName();
            ClassScanner classScanner = new ClassScanner(packagePath, predicateFilter);
            List<Class<?>> classList = classScanner.listScan();

            // 将扫描好的 action class 添加到业务框架中
            classList.forEach(builder::addActionController);
        }


        return builder;
    }
}
