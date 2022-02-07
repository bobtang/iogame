package com.iohao.example.client;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.action.skeleton.core.ParseType;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import lombok.experimental.UtilityClass;

/**
 * 客户端业务框架
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@UtilityClass
public class ExampleClientBarSkeleton {

    public BarSkeletonBuilder createBuilder(BarSkeletonBuilderParamConfig config) {
        // 尽量做到所有操作是可插拔的.
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.addInOut(new DebugInOut());

        // 使用 JAVA 解析类型
        builder.getSetting().setParseType(ParseType.JAVA);

        // action controller class. class has @ActionController
        config.scanClassActionController(builder::addActionController);

        return builder;
    }
}
