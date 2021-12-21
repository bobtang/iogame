package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.action.ActionCont;
import com.iohao.little.game.action.skeleton.core.action.BeeAction;
import com.iohao.little.game.action.skeleton.core.action.pojo.BeeApple;
import com.iohao.little.game.action.skeleton.core.data.TestDataKit;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;

@Slf4j
public class BarSkeletonTest {

    @Test
    public void newBuilder() {
        BarSkeleton barSkeleton = TestDataKit.newBarSkeleton();

        log.info("BarSkeleton");
        DefaultParamContext paramContext = new DefaultParamContext();
        RequestMessage requestMessage = new RequestMessage();

        CmdInfo cmdInfo = CmdInfoFlyweightFactory.me().getCmdInfo(ActionCont.BeeActionCont.CMD, ActionCont.BeeActionCont.HELLO);
        requestMessage.setCmdInfo(cmdInfo);

        BeeApple beeApple = new BeeApple();
        beeApple.setContent(" jackson ");
        beeApple.setId(101);
        requestMessage.setData(beeApple);

        barSkeleton.handle(paramContext, requestMessage);
        System.out.println();
        barSkeleton.handle(paramContext, requestMessage);
        System.out.println();
    }

    @Test
    public void codeLine() throws Exception {
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        BeeAction me = new BeeAction();
        Class<BeeAction> beeActionClass = BeeAction.class;

        BeeApple beeApple = new BeeApple();
        Method helloMethod = beeActionClass.getMethod("hello", BeeApple.class);
        Object invoke = helloMethod.invoke(me, beeApple);
        StackTraceElement[] steArray2 = Thread.currentThread().getStackTrace();
        System.out.println(invoke);


        Class<?> mapperInterface = BeeAction.class;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();


    }
}