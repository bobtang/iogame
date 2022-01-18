package com.iohao.little.game.net.external.session;

import io.netty.util.AttributeKey;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
public interface UserSessionAttr {
    /** 玩家id */
    AttributeKey<Long> userId = AttributeKey.valueOf("userId");
    /**
     * false 没有进行身份验证
     */
    AttributeKey<Boolean> verifyIdentity = AttributeKey.valueOf("verifyIdentity");


}
