package com.iohao.little.game.action.skeleton.core;

import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.Objects;

/**
 * 享元工厂
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public final class CmdInfoFlyweightFactory {
    /**
     * <pre>
     * key : cmdMerge
     * value : cmdInfo
     * </pre>
     */
    final Map<Integer, CmdInfo> cmdInfoMap = new NonBlockingHashMap<>();

    /**
     * 获取路由信息
     *
     * @param cmd    主路由
     * @param subCmd 子路由
     * @return 路由信息
     */
    public CmdInfo getCmdInfo(int cmd, int subCmd) {
        int cmdMerge = CmdKit.merge(cmd, subCmd);
        return getCmdInfo(cmd, subCmd, cmdMerge);
    }

    /**
     * 获取路由信息
     *
     * @param cmdMerge 主路由(高16) + 子路由(低16)
     * @return 路由信息
     */
    public CmdInfo getCmdInfo(int cmdMerge) {
        int cmd = CmdKit.getCmd(cmdMerge);
        int subCmd = CmdKit.getSubCmd(cmdMerge);
        return getCmdInfo(cmd, subCmd, cmdMerge);
    }

    private CmdInfo getCmdInfo(int cmd, int subCmd, int cmdMerge) {
        CmdInfo cmdInfo = cmdInfoMap.get(cmdMerge);
        // 无锁理念
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
