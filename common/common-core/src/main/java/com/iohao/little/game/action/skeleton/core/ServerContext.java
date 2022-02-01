package com.iohao.little.game.action.skeleton.core;

import com.alipay.remoting.AsyncContext;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;

import java.util.Collection;

/**
 * 当前服务器上下文
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ServerContext extends AsyncContext {
    void broadcast(FlowContext flowContext, Collection<Long> userIdList);
}
