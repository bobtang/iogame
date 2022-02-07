package com.iohao.game.collect.common;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
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
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.addInOut(new DebugInOut());

        // action send class. class has @DocActionSend
        config.scanClassActionSend(builder::addActionSend);

        // action controller class. class has @ActionController
        config.scanClassActionController(builder::addActionController);

        // 错误码相关的
        config.getMsgExceptionInfoList().forEach(builder::addMsgExceptionInfo);

        return builder;
    }
}
