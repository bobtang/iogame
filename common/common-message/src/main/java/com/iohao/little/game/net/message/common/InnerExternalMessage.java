package com.iohao.little.game.net.message.common;

import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.Data;

/**
 * 来自外部服务器的请求
 * <pre>
 *     一般用于转发到逻辑服
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-11
 */
@Data
public class InnerExternalMessage {
    RequestMessage requestMessage;
}
