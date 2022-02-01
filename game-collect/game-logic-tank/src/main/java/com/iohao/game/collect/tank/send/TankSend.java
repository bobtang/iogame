package com.iohao.game.collect.tank.send;

import com.iohao.game.collect.common.send.AbstractFlowContextSend;
import com.iohao.little.game.action.skeleton.annotation.DocActionSend;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 坦克相关推送
 *
 * @author 洛朱
 * @date 2022-01-31
 */
@Slf4j
@DocActionSend(cmd = 0, subCmd = 0, dataClass = TankSend.class)
public class TankSend extends AbstractFlowContextSend {

    public TankSend(FlowContext flowContext) {
        super(flowContext);
    }

    @Override
    protected void trick() {
        log.info("推送 {}", this.flowContext.getMethodResult());
    }
}
