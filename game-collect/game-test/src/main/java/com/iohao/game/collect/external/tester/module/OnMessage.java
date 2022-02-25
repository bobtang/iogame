package com.iohao.game.collect.external.tester.module;

import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
public interface OnMessage {
    Object process(ExternalMessage message, byte[] data);

    int getCmdMerge();

    Object requestData();

    default ExternalMessage createExternalMessage() {

        ExternalMessage request = new ExternalMessage();
        request.setCmdCode( 1);
        request.setProtocolSwitch(0);

        request.setCmdMerge(this.getCmdMerge());

        request.setData(this.requestData());

        return request;
    }

}
