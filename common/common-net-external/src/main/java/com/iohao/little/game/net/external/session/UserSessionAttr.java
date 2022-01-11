package com.iohao.little.game.net.external.session;

import io.netty.util.AttributeKey;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public interface UserSessionAttr {
    /** 玩家id */
    AttributeKey<Long> userId = AttributeKey.valueOf("userId");

}
