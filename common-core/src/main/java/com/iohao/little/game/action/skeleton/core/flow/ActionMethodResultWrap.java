package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * ActionMethod 结果包装器
 */
public interface ActionMethodResultWrap<Request extends RequestMessage, Response extends ResponseMessage> {
    /**
     * 包装结果
     *
     * @param actionCommand 命令
     * @param request         请求
     * @param result          响应
     * @return 包装后的响应结果
     */
    Response wrap(ActionCommand actionCommand, Request request, Object result);
}
