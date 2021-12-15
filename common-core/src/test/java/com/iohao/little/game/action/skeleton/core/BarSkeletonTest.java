package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.action.ActionCont;
import com.iohao.little.game.action.skeleton.core.data.TestDataKit;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class BarSkeletonTest {

    @Test
    public void newBuilder() {
        BarSkeleton barSkeleton = TestDataKit.newBarSkeleton();

        log.info("BarSkeleton");
        DefaultParamContext paramContext = new DefaultParamContext();
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmd(ActionCont.BeeActionCont.CMD);
        requestMessage.setSubCmd(ActionCont.BeeActionCont.HELLO);


        barSkeleton.handle(paramContext, requestMessage);

        System.out.println();

    }

}