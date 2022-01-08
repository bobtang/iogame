package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.*;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;


/**
 * 整个核心的骨架.积木骷髅 (业务框架)
 * <p>
 * 其实这就是一个积木骷髅的游戏.
 * <pre>
 * - ta可以处理所有来访者 {@link Handler}, 你可以为ta添加多种处理链,直到你满意为止.
 * - ta可以拿起笔和本子 {@link ActionMethodInOut}, 记录所看到的东西.
 * - 处理完后你会让ta如何收尾 {@link ActionAfter}
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@Accessors(chain = true)
@Setter
@Getter
public class BarSkeleton {

    /** ActionCommandManager */
    final ActionCommandManager actionCommandManager = new ActionCommandManager();
    /** handlerList */
    final List<Handler<RequestMessage>> handlers = new ArrayList<>();
    /** inoutList */
    final List<ActionMethodInOut> inOuts = new ArrayList<>();

    /** true : 开放拦截 in */
    boolean openIn;
    /** true : 开放拦截 out */
    boolean openOut;

    /** 命令执行器 */
    ActionCommandFlowExecute actionCommandFlowExecute;
    /** tcp action 对象创建工厂 */
    ActionControllerFactoryBean<Object> actionControllerFactoryBean;
    /** InvokeActionMethod */
    ActionMethodInvoke actionMethodInvoke;
    /** 方法参数解析器 */
    ActionMethodParamParser actionMethodParamParser;
    /** 异常处理 */
    ActionMethodExceptionProcess actionMethodExceptionProcess;
    /** 结果包装器 */
    ActionMethodResultWrap<RequestMessage, ResponseMessage> actionMethodResultWrap;
    /** 框架执行完后, 最后需要做的事. 一般用于write数据到客户端 */
    ActionAfter<RequestMessage, ResponseMessage> actionAfter;

    /**
     * true 只有一个 handler
     *
     * @see Handler
     */
    boolean singleHandler;
    Handler<RequestMessage> handler;

    BarSkeleton() {

    }

    public static BarSkeletonBuilder newBuilder() {
        return new BarSkeletonBuilder();
    }

    public void handle(ParamContext paramContext, RequestMessage request) {
        if (singleHandler) {
            handler.handler(paramContext, request, this);
        } else {
            for (Handler<RequestMessage> handler : handlers) {
                if (!handler.handler(paramContext, request, this)) {
                    return;
                }
            }
        }
    }

}
