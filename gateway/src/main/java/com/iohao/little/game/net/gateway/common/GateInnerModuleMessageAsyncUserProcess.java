package com.iohao.little.game.net.gateway.common;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.gateway.module.ModuleInfoManager;
import com.iohao.little.game.net.message.common.InnerModuleMessage;

public class GateInnerModuleMessageAsyncUserProcess extends AsyncUserProcessor<InnerModuleMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, InnerModuleMessage innerModuleMessage) {
        // 模块之间的请求处理
        var requestMessage = innerModuleMessage.getRequestMessage();

        // 根据 cmdInfo 查找出可以处理请求的模块
        var cmdInfo = requestMessage.getCmdInfo();
        var moduleInfo = ModuleInfoManager.me().getModuleInfo(cmdInfo.getCmdMerge());

        try {
            // 请求方 请求其它服务器得到的响应数据
            var responseMessage = (ResponseMessage) moduleInfo.invokeSync(requestMessage);
            // 将响应数据给回请求方
            asyncCtx.sendResponse(responseMessage);
        } catch (RemotingException | InterruptedException e) {
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
        return InnerModuleMessage.class.getName();
    }
}
