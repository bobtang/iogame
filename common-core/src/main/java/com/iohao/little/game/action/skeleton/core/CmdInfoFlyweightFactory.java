package com.iohao.little.game.action.skeleton.core;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元工厂
 */
public final class CmdInfoFlyweightFactory {
    /**
     * <pre>
     * key : cmdMerge
     * value : cmdInfo
     * </pre>
     */
    final Map<Integer, CmdInfo> cmdInfoMap = new ConcurrentHashMap<>();

    public CmdInfo getCmdInfo(int cmd, int subCmd) {
        int cmdMerge = CmdKit.merge(cmd, subCmd);
        return getCmdInfo(cmd, subCmd, cmdMerge);
    }

    public CmdInfo getCmdInfo(int cmdMerge) {
        int cmd = CmdKit.getCmd(cmdMerge);
        int subCmd = CmdKit.getSubCmd(cmdMerge);
        return getCmdInfo(cmd, subCmd, cmdMerge);
    }

    private CmdInfo getCmdInfo(int cmd, int subCmd, int cmdMerge) {
        CmdInfo cmdInfo = cmdInfoMap.get(cmdMerge);
        if (Objects.isNull(cmdInfo)) {
            cmdInfo = new CmdInfo(cmd, subCmd);
            cmdInfo = cmdInfoMap.putIfAbsent(cmdMerge, cmdInfo);
            if (Objects.isNull(cmdInfo)) {
                cmdInfo = cmdInfoMap.get(cmdMerge);
            }
        }
        return cmdInfo;
    }

    public static CmdInfoFlyweightFactory me() {
        return Holder.ME;
    }

    private CmdInfoFlyweightFactory() {
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final CmdInfoFlyweightFactory ME = new CmdInfoFlyweightFactory();
    }
}
