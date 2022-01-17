package com.iohao.little.game.action.skeleton.core.flow.interal;

import com.iohao.little.game.action.skeleton.core.flow.ResponseMessageCreate;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
public class DefaultResponseMessageCreate implements ResponseMessageCreate {
    @Override
    public ResponseMessage createResponseMessage() {
        return new ResponseMessage();
    }
}
