package com.iohao.little.game.net.message.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleKeyManager {

    /**
     * key : cmdMerge
     * value : moduleKey
     */
    Map<Integer, ModuleKey> cmdMergeModuleKeyMap = new ConcurrentHashMap<>();

    /**
     * cmdMerge 与 moduleKey 关联
     * <pre>
     *     之后可以通过 cmdMerge 查找 moduleKey
     * </pre>
     *
     * @param cmdMergeArray cmdMergeArray
     * @param moduleKey     moduleKey
     */
    public void relation(int[] cmdMergeArray, ModuleKey moduleKey) {
        for (int cmdMerge : cmdMergeArray) {
            cmdMergeModuleKeyMap.put(cmdMerge, moduleKey);
        }
    }

    /**
     * 通过 cmdMerge 查找 moduleKey
     *
     * @param cmdMerge cmdMerge
     * @return moduleKey
     */
    public ModuleKey getModuleKeyByCmdMerge(int cmdMerge) {
        return cmdMergeModuleKeyMap.get(cmdMerge);
    }

    /**
     * 确保只有一个服的时候，可以使用该方法
     *
     * @param moduleId 模块id
     * @return ModuleInfoKey
     */
    public ModuleKey getModuleKey(int moduleId) {
        return getModuleKey(moduleId, 0);
    }

    /**
     * 取同业务模块下的服务器 - 根据序列id获取
     *
     * @param moduleId   模块id
     * @param sequenceId 序列id
     * @return ModuleInfoKey
     */
    public ModuleKey getModuleKey(int moduleId, int sequenceId) {
        return ModuleKeyFlyweightFactory.me().getModuleKey(moduleId, sequenceId);
    }

    public static ModuleKeyManager me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final ModuleKeyManager ME = new ModuleKeyManager();
    }

}
