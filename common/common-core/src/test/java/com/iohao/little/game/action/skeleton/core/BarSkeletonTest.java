package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.action.ExampleActionCont;
import com.iohao.little.game.action.skeleton.core.action.pojo.BeeApple;
import com.iohao.little.game.action.skeleton.core.action.pojo.Bird;
import com.iohao.little.game.action.skeleton.core.data.TestDataKit;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class BarSkeletonTest {

    @Test
    public void newBuilder() {

        // 模拟路由信息
        CmdInfo cmdInfo = CmdInfoFlyweightFactory.me().getCmdInfo(ExampleActionCont.BeeActionCont.cmd, ExampleActionCont.BeeActionCont.hello);

        // 模拟请求
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);

        // 模拟请求数据 （一般由前端传入）
        BeeApple beeApple = new BeeApple();
        beeApple.setContent("hello 塔姆!");
        beeApple.setId(101);
        // 把模拟请求的数据,放入请求对象中
        requestMessage.setData(beeApple);

        log.info("BarSkeleton");
        DefaultParamContext paramContext = new DefaultParamContext();

        // 构建业务框架
        BarSkeleton barSkeleton = TestDataKit.newBarSkeleton();

        // 业务框架处理用户请求
        barSkeleton.handle(paramContext, requestMessage);
        System.out.println();

        // 业务框架处理用户请求
        barSkeleton.handle(paramContext, requestMessage);
        System.out.println();
    }

    @Test
    public void testVoid() {
        // 模拟路由信息
        CmdInfo cmdInfo = CmdInfoFlyweightFactory.me().getCmdInfo(ExampleActionCont.BirdActionCont.cmd, ExampleActionCont.BirdActionCont.test_void);


        // 模拟请求
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);

        // 模拟请求数据 （一般由前端传入）
        Bird bird = new Bird();
        bird.setName("hello 塔姆!");
        bird.setId(1010);
        // 把模拟请求的数据,放入请求对象中
        requestMessage.setData(bird);

        DefaultParamContext paramContext = new DefaultParamContext();

        // 构建业务框架
        BarSkeleton barSkeleton = TestDataKit.newBarSkeleton();
        // 业务框架处理用户请求
        barSkeleton.handle(paramContext, requestMessage);
        System.out.println();

    }
}