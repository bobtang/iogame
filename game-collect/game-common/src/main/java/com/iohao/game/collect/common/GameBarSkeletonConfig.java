package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.action.skeleton.core.ParseType;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.little.game.common.kit.ClassScanner;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 实践案例游戏的 业务框架 配置
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@UtilityClass
public class GameBarSkeletonConfig {

    public BarSkeletonBuilder createBuilder(BarSkeletonBuilderParamConfig config) {
        // 尽量做到所有操作是可插拔的. (InOut 插件)
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.addInOut(new DebugInOut());

        // 使用 pb
        builder.getSetting().setParseType(ParseType.PB);

        // action send class. class has @DocActionSend
        scanClass(config.getActionSendClassList(), config.getActionSendPredicateFilter(), builder::addActionSendController);

        // action controller class. class has @ActionController
        scanClass(config.getActionControllerClassList(), config.getActionControllerPredicateFilter(), builder::addActionController);


        return builder;
    }

    private void scanClass(List<Class<?>> actionList
            , Predicate<Class<?>> predicateFilter
            , Consumer<Class<?>> actionConsumer) {

        for (Class<?> actionClazz : actionList) {
            // 扫描
            String packagePath = actionClazz.getPackageName();
            ClassScanner classScanner = new ClassScanner(packagePath, predicateFilter);
            List<Class<?>> classList = classScanner.listScan();

            // 将扫描好的 class 添加到业务框架中
            classList.forEach(actionConsumer);
        }
    }
}
