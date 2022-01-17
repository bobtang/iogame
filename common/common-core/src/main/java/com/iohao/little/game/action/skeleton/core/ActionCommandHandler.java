package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.Setter;

/**
 * 该handler用于执行 {@link ActionCommand} 对象
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
public final class ActionCommandHandler implements Handler {

    @Setter
    BarSkeleton barSkeleton;

    @Override
    public boolean handler(ParamContext paramContext, RequestMessage request, BarSkeleton barSkeleton) {
        /*
        这里不做任何null判断了. 使用者们自行注意
        根据客户端的请求信息,获取对应的命令对象来处理这个请求
         */
        var actionCommandManager = barSkeleton.actionCommandManager;
        // 通过路由获取处理请求的 action
        var cmd = request.getCmd();
        var subCmd = request.getSubCmd();
        var actionCommand = actionCommandManager.getActionCommand(cmd, subCmd);

        // 命令流程执行器
        var actionCommandFlowExecute = barSkeleton.getActionCommandFlowExecute();
        actionCommandFlowExecute.execute(paramContext, actionCommand, request, barSkeleton);

        return true;
    }
}
