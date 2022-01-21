package com.iohao.little.game.action.skeleton.protocol;

import java.io.Serial;

/**
 * 请求参数
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class RequestMessage extends BarMessage {
    @Serial
    private static final long serialVersionUID = 8564408386704453534L;

    public ResponseMessage createResponseMessage() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCmdInfo(this.getCmdInfo());
        responseMessage.setUserId(this.userId);
        responseMessage.setRpcCommandType(this.rpcCommandType);
        return responseMessage;
    }
}
