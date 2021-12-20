package com.iohao.little.game.action.skeleton.core.flow.interal;

import cn.hutool.core.util.StrUtil;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;


/**
 * 统计actionCommand执行时间 <BR>
 * 该类不是线程安全的 <BR>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
public class ExecuteTimeInOut implements ActionMethodInOut<RequestMessage, ResponseMessage> {
    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    public void fuckIn(ParamContext paramContext, ActionCommand actionCommand, Object actionController, RequestMessage requestMessage) {
        threadLocal.set(System.currentTimeMillis());
    }

    @Override
    public void fuckOut(ParamContext paramContext, ActionCommand actionCommand, Object actionController, RequestMessage requestMessage, ResponseMessage responseCommand) {
        long ms = System.currentTimeMillis() - threadLocal.get();
        threadLocal.remove();

        Class<?> cc = actionCommand.getActionControllerClazz();

        String template = "业务方法总耗时: {}ms .({}.java:1).{}";

        String message = StrUtil.format(template, ms, cc.getSimpleName(), actionCommand.getActionMethodName());
        System.out.println(message);
    }
}
