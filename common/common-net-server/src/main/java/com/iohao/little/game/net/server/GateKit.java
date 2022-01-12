package com.iohao.little.game.net.server;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * 网关工具
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@UtilityClass
public class GateKit {
    @Getter
    @Setter
    private volatile BoltServer boltServer;

    /**
     * 将请求转发到逻辑服
     *
     * @param asyncCtx       asyncCtx
     * @param requestMessage 请求
     */
    public void sendToLogicServer(AsyncContext asyncCtx, RequestMessage requestMessage) {
        var cmdInfo = requestMessage.getCmdInfo();
        // 根据 cmdInfo 查找出可以处理请求的模块
        var moduleInfo = ModuleInfoManager.me().getModuleInfo(cmdInfo);

        try {
            // 请求方 请求其它服务器得到的响应数据
            var responseMessage = (ResponseMessage) moduleInfo.invokeSync(requestMessage);
            // 将响应数据给回请求方
            asyncCtx.sendResponse(responseMessage);
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
