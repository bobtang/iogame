package com.iohao.little.game.net.external.bootstrap.handler;

import cn.hutool.core.util.IdUtil;
import com.alipay.remoting.rpc.RpcCommandType;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import com.iohao.little.game.net.external.session.UserSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
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
        requestMessage.setDataContent(message.getData());
        requestMessage.setRpcCommandType(RpcCommandType.REQUEST_ONEWAY);

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

        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        ByteBuf byteBuf = Unpooled.buffer(headLen);
        ExternalEncoder.encode(message, byteBuf);

        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(byteBuf);

        channel.writeAndFlush(binaryWebSocketFrame);
    }

    public long newId() {

        IdUtil.getSnowflake().nextId();

        return IdUtil.getSnowflake().nextId();
    }

}
