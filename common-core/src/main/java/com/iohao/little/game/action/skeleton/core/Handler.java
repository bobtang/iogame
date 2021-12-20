package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.protocol.RequestMessage;

/**
 * 业务框架处理器
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface Handler<Request extends RequestMessage> {
    /**
     * 处理一个action请求
     *
     * @param paramContext 参数上下文
     * @param request      客户端请求信息
     * @param barSkeleton  骨架
     * @return 如果返回 false 就不交给下一个链进行处理. 全剧终了.
     */
    boolean handler(ParamContext paramContext, Request request, BarSkeleton barSkeleton);
}