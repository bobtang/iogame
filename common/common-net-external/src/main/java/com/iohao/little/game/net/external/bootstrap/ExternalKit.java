package com.iohao.little.game.net.external.bootstrap;

import cn.hutool.core.util.IdUtil;
import com.alipay.remoting.rpc.RpcCommandType;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-18
 */
@UtilityClass
public class ExternalKit {
    public RequestMessage convertRequestMessage(ExternalMessage message) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdMerge(message.getCmdMerge());
        requestMessage.setRpcCommandType(RpcCommandType.REQUEST_ONEWAY);
        requestMessage.setDataContent(message.getDataContent());

        return requestMessage;
    }

    public ExternalMessage convertExternalMessage(ResponseMessage responseMessage) {

        ExternalMessage externalMessage = new ExternalMessage();
        externalMessage.setCmdCode((short) 1);
        externalMessage.setCmdMerge(responseMessage.getCmdMerge());
        externalMessage.setResponseStatus((short) responseMessage.getErrorCode());
        externalMessage.setData(responseMessage.getDataContent());

        return externalMessage;
    }

    public void writeAndFlush(long userId, ExternalMessage message) {
        UserSession session = UserSession.me();
        Channel channel = session.getChannel(userId);

        if (Objects.isNull(channel)) {
            // TODO: 2022/1/16 用户不在线, 可以做点其他事

            return;
        }

        channel.writeAndFlush(message);

    }

    public long newId() {

        IdUtil.getSnowflake().nextId();

        return IdUtil.getSnowflake().nextId();
    }

}
