package com.iohao.little.game.action.skeleton.core.flow;

/**
 * ActionMethod 结果包装器
 * <pre>
 *     将 action 业务方法产生的结果或者错误码 包装到响应(ResponseMessage)对象中 。
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodResultWrap {
    /**
     * 包装结果
     * <pre>
     *     将 action 业务方法产生的结果或者错误码 包装到响应(ResponseMessage)对象中 。
     * </pre>
     *
     * @param flowContext flow 上下文
     */
    void wrap(FlowContext flowContext);
}
