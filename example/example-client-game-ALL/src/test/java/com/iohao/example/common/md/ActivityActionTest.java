package com.iohao.example.common.md;

import com.iohao.little.game.action.skeleton.core.*;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 洛朱
 * @date 2021-12-22
 */
public class ActivityActionTest {

    BarSkeleton barSkeleton;

    @Before
    public void setUp() throws Exception {
        // 构建业务框架
        barSkeleton = newBarSkeleton();
    }

    @Test
    public void hello() {
        // 模拟路由信息
        CmdInfo cmdInfo = CmdInfoFlyweightFactory.me().getCmdInfo(ActivityModule.cmd, ActivityModule.hello);

        // 模拟前端请求
        RequestMessage requestMessage =  new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);

        // 模拟请求数据 （一般由前端传入）
        Active active = new Active();
        active.setName("塔姆");
        active.setId(101);

        // 把模拟请求的数据,放入请求对象中
        requestMessage.setData(active);

        // 上面都是由前端传入， 实际的代码从这里开始
        // 业务框架处理用户请求
        barSkeleton.handle(new DefaultParamContext(), requestMessage);
    }

    private BarSkeleton newBarSkeleton() {
        // 尽量做到所有操作是可插拔的. 详细配置 see BarSkeletonBuilder.build
        BarSkeletonBuilder builder = BarSkeleton.newBuilder();
        builder.getBarSkeletonSetting().setPrintActionShort(true);

        // 添加(请求响应)处理类. 用户可以定义自己的业务控制器 - 这里推荐实现扫描包的形式添加 tcp 处理类
        builder.addActionController(ActivityAction.class);

        BarSkeleton barSkeleton = builder.build();

        return barSkeleton;
    }

}