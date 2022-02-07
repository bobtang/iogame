package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.Connection;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.GateKit;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 对外服务器消息处理
 * <pre>
 *     接收真实用户的请求，把请求转发到逻辑服
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-11
 */
@Slf4j
public class GateExternalRequestMessageAsyncUserProcessor extends AsyncUserProcessor<RequestMessage> {
    final BoltServer boltServer;

    public GateExternalRequestMessageAsyncUserProcessor(BoltServer boltServer) {
        this.boltServer = boltServer;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, RequestMessage requestMessage) {
        // 把对外服的请求转发到逻辑服 处理

        log.info("把对外服的请求转发到逻辑服 处理 {}", requestMessage);
        ModuleInfoProxy moduleInfo = GateKit.getModuleInfo(requestMessage);

        if (Objects.isNull(moduleInfo)) {
            //  通知对外服， 路由不存在
            extracted(bizCtx, requestMessage);
            return;
        }

        try {
            moduleInfo.oneway(requestMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void extracted(BizContext bizCtx, RequestMessage requestMessage) {
        // 路由不存在
        Connection connection = bizCtx.getConnection();
        ResponseMessage responseMessage = requestMessage.createResponseMessage();

        ActionErrorEnum errorCode = ActionErrorEnum.cmdInfoErrorCode;
        responseMessage.setValidatorMsg(errorCode.getMsg());
        responseMessage.setResponseStatus(errorCode.getCode());

        RpcServer rpcServer = boltServer.getRpcServer();
        try {
            rpcServer.oneway(connection, responseMessage);
        } catch (RemotingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     * 假设 除了需要处理 MyRequest 类型的数据，还要处理 java.lang.String 类型，有两种方式：
     * 1、再提供一个 UserProcessor 实现类，其 interest() 返回 java.lang.String.class.getName()
     * 2、使用 MultiInterestUserProcessor 实现类，可以为一个 UserProcessor 指定 List<String> multiInterest()
     */
    @Override
    public String interest() {
        return RequestMessage.class.getName();
    }
}
