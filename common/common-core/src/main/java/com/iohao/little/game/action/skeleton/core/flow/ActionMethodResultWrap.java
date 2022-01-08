package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * ActionMethod 结果包装器
 *
 * @param <Request>  请求参数
 * @param <Response> 响应
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ActionMethodResultWrap<Request extends RequestMessage, Response extends ResponseMessage> {
    /**
     * 包装结果
     * <pre>
     *     把用户的业务方法返回值包装成 Response
     * </pre>
     *
     * @param actionCommand 命令
     * @param request       请求
     * @param result        响应
     * @return 包装后的响应结果
     */
    Response wrap(ActionCommand actionCommand, Request request, Object result);
}
