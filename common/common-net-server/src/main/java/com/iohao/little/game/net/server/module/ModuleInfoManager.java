package com.iohao.little.game.net.server.module;

import com.iohao.little.game.net.message.common.ModuleMessage;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块信息管理
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class ModuleInfoManager {
    /**
     * key : cmdMerge
     * value : moduleId
     */
    Map<Integer, Integer> moduleIdMap = new ConcurrentHashMap<>();
    Map<Integer, ModuleInfoRegion> moduleInfoRegionMap = new ConcurrentHashMap<>();

    public ModuleInfoProxy getModuleInfo(int cmdMerge) {
        Integer moduleId = moduleIdMap.get(cmdMerge);


        ModuleInfoRegion moduleInfoRegion = moduleInfoRegionMap.get(moduleId);

        if (Objects.isNull(moduleInfoRegion)) {
            return null;
        }

        return moduleInfoRegion.getModuleInfo();
    }

    public void addModuleInfo(ModuleMessage moduleMessage) {

        var moduleId = moduleMessage.getModuleKey().moduleId;

        ModuleInfoRegion moduleInfoRegion = moduleInfoRegionMap.get(moduleId);
        if (Objects.isNull(moduleInfoRegion)) {
            moduleInfoRegion = new ModuleInfoRegion();
            moduleInfoRegion = moduleInfoRegionMap.putIfAbsent(moduleId, moduleInfoRegion);
            if (Objects.isNull(moduleInfoRegion)) {
                moduleInfoRegion = moduleInfoRegionMap.get(moduleId);
            }
        }

        moduleInfoRegion.addModuleInfo(moduleMessage);

        int[] cmdMergeArray = moduleMessage.getCmdMergeArray();

        for (int cmdMerge : cmdMergeArray) {
            moduleIdMap.put(cmdMerge, moduleId);
        }
    }

    public static ModuleInfoManager me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final ModuleInfoManager ME = new ModuleInfoManager();
    }


}
