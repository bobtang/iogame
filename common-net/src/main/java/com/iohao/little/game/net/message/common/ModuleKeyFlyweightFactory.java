package com.iohao.little.game.net.message.common;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ModuleInfoKey 享元工厂
 */
final class ModuleKeyFlyweightFactory {
    /**
     * <pre>
     * key : moduleId
     * value : ModuleInfoKeyRegion
     * </pre>
     */
    private final Map<Integer, ModuleKeyRegion> regionMap = new ConcurrentHashMap<>();

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
        ModuleKeyRegion region = getModuleKeyRegion(moduleId);
        return region.getModuleKey(sequenceId);
    }

    private ModuleKeyRegion getModuleKeyRegion(int moduleId) {
        ModuleKeyRegion keyRegion = regionMap.get(moduleId);

        if (Objects.isNull(keyRegion)) {
            keyRegion = new ModuleKeyRegion(moduleId);
            keyRegion = regionMap.putIfAbsent(moduleId, keyRegion);
            if (Objects.isNull(keyRegion)) {
                keyRegion = regionMap.get(moduleId);
            }
        }

        return keyRegion;
    }

    private static class ModuleKeyRegion {
        /**
         * <pre>
         * key : sequenceId
         * value : ModuleInfoKey
         * </pre>
         */
        private final Map<Integer, ModuleKey> map = new ConcurrentHashMap<>();

        final int moduleId;

        ModuleKeyRegion(int moduleId) {
            this.moduleId = moduleId;
        }

        ModuleKey getModuleKey(int sequenceId) {
            ModuleKey moduleKey = map.get(sequenceId);

            if (Objects.isNull(moduleKey)) {
                moduleKey = new ModuleKey(moduleId, sequenceId);
                moduleKey = map.putIfAbsent(sequenceId, moduleKey);
                if (Objects.isNull(moduleKey)) {
                    moduleKey = map.get(sequenceId);
                }
            }

            return moduleKey;
        }
    }

    public static ModuleKeyFlyweightFactory me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final ModuleKeyFlyweightFactory ME = new ModuleKeyFlyweightFactory();
    }
}
