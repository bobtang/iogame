package com.iohao.little.game.net.client.kit;

import com.alipay.remoting.exception.RemotingException;
import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ChangeUserIdMessage;
import com.iohao.little.game.net.message.common.ChangeUserIdMessageResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 变更用户 id
 *
 * @author 洛朱
 * @date 2022-01-19
 */
@Slf4j
@UtilityClass
public class ChangeUserIdKit {
    /**
     * 变更用户的 userId
     *
     * @param flowContext 业务框架 flow上下文
     * @param newUserId   一般从数据库中获取
     * @return true 变更成功
     */
    public boolean changeUserId(FlowContext flowContext, long newUserId) {
        // 这个 userId 一般是首次建立连接时，系统随机分配的临时 id
        long userId = flowContext.getUserId();

        ChangeUserIdMessage userIdMessage = new ChangeUserIdMessage();
        userIdMessage.setUserId(userId);
        userIdMessage.setNewUserId(newUserId);

        log.info("1 逻辑服 {}", userIdMessage);

        try {
            DefaultParamContext paramContext = flowContext.getParamContext();
            BoltClientProxy boltClientProxy = (BoltClientProxy) paramContext.getServerContext();
            ChangeUserIdMessageResponse changeUserIdMessageResponse = (ChangeUserIdMessageResponse) boltClientProxy
                    .invokeSync(userIdMessage);

            log.info("5 逻辑服 {}", changeUserIdMessageResponse);

            if (Objects.isNull(changeUserIdMessageResponse) || !changeUserIdMessageResponse.isSuccess()) {
                return false;
            }
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

        ResponseMessage response = flowContext.getResponse();
        response.setUserId(newUserId);

        return true;
    }
}
