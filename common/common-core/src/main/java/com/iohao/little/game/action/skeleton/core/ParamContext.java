package com.iohao.little.game.action.skeleton.core;

/**
 * 参数上下文
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ParamContext {
    /**
     * 当前项目启动的服务上下文
     *
     * @return 当前项目启动的服务上下文
     */
    ServerContext getServerContext();
}
