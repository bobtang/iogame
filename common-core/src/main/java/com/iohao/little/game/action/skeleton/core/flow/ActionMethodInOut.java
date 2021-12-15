package com.iohao.little.game.action.skeleton.core.flow;


import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ParamContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * inout 接口
 * <pre>
 * {@link ActionCommand} 执行前,后的逻辑钩子类
 *
 * 毫无疑问的是这个类的方法名过于刺激,但并不会影响我们的发挥
 *
 * 通过这个接口,你可以做很多事情, 当然这要看你的想象力有多丰富了
 *     例如: 日志记录, 执行时间打印. 等等 (可参考框架内置的实现类)
 * </pre>
 *
 * @author 洛朱
 * @date 2021/12/12
 */
public interface ActionMethodInOut<Request extends RequestMessage, Response extends ResponseMessage> {

    /**
     * <pre>
     * fuck前
     * 这个方法不要做耗时计算, 因为是在执行你的业务方法前运行的.
     * 建议做一些时间记录等非耗时运算
     * </pre>
     *
     * @param paramContext     参数上下文
     * @param actionCommand    command
     * @param actionController 控制器类对象
     * @param request          请求对象
     */
    void fuckIn(ParamContext paramContext, ActionCommand actionCommand, Object actionController, Request request);

    /**
     * <pre>
     * fuck后
     * 当执行这个方法时, 已经把响应数据发送到客户端了
     * </pre>
     *
     * @param paramContext     参数上下文
     * @param actionCommand    command
     * @param actionController 控制器类对象
     * @param request          请求对象
     * @param response         响应对象
     */
    void fuckOut(ParamContext paramContext, ActionCommand actionCommand, Object actionController, Request request, Response response);

}
