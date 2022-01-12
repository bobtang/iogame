package com.iohao.little.game.net.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 模块注册
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Slf4j
public class GateRegisterModuleMessageAsyncUserProcessor extends AsyncUserProcessor<ModuleMessage> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ModuleMessage moduleMessage) {
        String remoteAddress = bizCtx.getRemoteAddress();
        moduleMessage.setAddress(remoteAddress);

        log.info("--------------------------------- 模块注册 {}", moduleMessage);

        ModuleInfoManager.me().addModuleInfo(moduleMessage);
    }

    /**
     * 指定感兴趣的请求数据类型，该 UserProcessor 只对感兴趣的请求类型的数据进行处理；
     * 假设 除了需要处理 MyRequest 类型的数据，还要处理 java.lang.String 类型，有两种方式：
     * 1、再提供一个 UserProcessor 实现类，其 interest() 返回 java.lang.String.class.getName()
     * 2、使用 MultiInterestUserProcessor 实现类，可以为一个 UserProcessor 指定 List<String> multiInterest()
     */
    @Override
    public String interest() {
        return ModuleMessage.class.getName();
    }
}
