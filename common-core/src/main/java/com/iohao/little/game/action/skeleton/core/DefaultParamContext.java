package com.iohao.little.game.action.skeleton.core;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import lombok.Data;

/**
 * 参数上下文
 * <pre>
 *     bolt 中获取的一些上下文
 *     目前只使用了 AsyncContext 来推送数据
 *
 *     对于这些上下文不理解问题不大，只是用来处理一特殊业务
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Data
public class DefaultParamContext implements ParamContext {
    /** bolt biz 上下文 */
    BizContext bizCtx;
    /** bolt async 上下文 */
    AsyncContext asyncCtx;
    /** 当前项目启动的服务上下文 */
    ServerContext serverContext;
}
