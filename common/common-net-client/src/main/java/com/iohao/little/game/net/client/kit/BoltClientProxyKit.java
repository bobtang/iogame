package com.iohao.little.game.net.client.kit;

import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ModuleKey;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
@UtilityClass
public class BoltClientProxyKit {
    Map<ModuleKey, BoltClientProxy> boltClientProxyMap = new HashMap<>();

    public void put(ModuleKey moduleKey, BoltClientProxy boltClientProxy) {
        boltClientProxyMap.put(moduleKey, boltClientProxy);
    }

    public BoltClientProxy get(ModuleKey moduleKey) {
        return boltClientProxyMap.get(moduleKey);
    }
}
