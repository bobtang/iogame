package com.iohao.little.game.action.skeleton.core;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import lombok.Data;

/**
 * 参数上下文
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Data
public class DefaultParamContext implements ParamContext {
    BizContext bizCtx;
    AsyncContext asyncCtx;
    ServerContext serverContext;
}
