package com.iohao.little.game.net.external.bootstrap.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
@Getter
@Setter
public class ExternalResponse extends ExternalMessage {
    /**
     * 响应码。
     * <pre>
     *     从字段精简的角度，我们不可能每次响应都带上完整的异常栈给客户端排查问题，
     *     因此，我们会定义一些响应码，通过编号进行网络传输，方便客户端定位问题。
     *
     *     0:成功
     *     >0: 有错误
     * </pre>
     */
    short responseStatus;
}
