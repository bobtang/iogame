package com.iohao.little.game.widget.broadcast.internal;

import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.widget.broadcast.BroadcastMessageContext;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
public record ClientBroadcastMessageContext(
        BoltClientProxy boltClientProxy) implements BroadcastMessageContext {
}
