package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import lombok.experimental.UtilityClass;

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
        BarSkeletonBuilder builder = config.createBuilder();

        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());

        return builder;
    }
}
