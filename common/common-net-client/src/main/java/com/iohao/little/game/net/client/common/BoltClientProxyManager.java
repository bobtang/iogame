package com.iohao.little.game.net.client.common;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.net.message.common.ModuleKey;
import com.iohao.little.game.net.message.common.ModuleKeyKit;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端代理管理器
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class BoltClientProxyManager {
    private final Map<ModuleKey, BoltClientProxy> boltClientMap = new ConcurrentHashMap<>();
    private BoltClientProxy gateProxy;

    public BoltClientProxy getBoltClientProxy(ModuleKey moduleKey) {

        if (Objects.isNull(moduleKey)) {
            // 把数据传到网关, is null 通常是请求其它服务器的数据
            return gateProxy;
        }

        BoltClientProxy boltClientProxy = boltClientMap.get(moduleKey);
        // 无锁化
        if (Objects.isNull(boltClientProxy)) {
            boltClientProxy = new BoltClientProxy();
            boltClientProxy = boltClientMap.putIfAbsent(moduleKey, boltClientProxy);

            if (Objects.isNull(boltClientProxy)) {
                boltClientProxy = boltClientMap.get(moduleKey);
            }

            // TODO: 这种情况只在全部游戏服务器都启动在一个进程时才会用得上
            gateProxy = boltClientProxy;
        }

        return boltClientProxy;
    }

    public BoltClientProxy getBoltClientProxy(CmdInfo cmdInfo) {
        ModuleKey moduleKey = ModuleKeyKit.getModuleKeyByCmdMerge(cmdInfo.getCmdMerge());
        return getBoltClientProxy(moduleKey);
    }

    public static BoltClientProxyManager me() {
        return Holder.ME;
    }

    private BoltClientProxyManager() {
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final BoltClientProxyManager ME = new BoltClientProxyManager();
    }
}
