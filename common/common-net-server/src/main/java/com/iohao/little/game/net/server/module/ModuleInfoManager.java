package com.iohao.little.game.net.server.module;

import com.iohao.little.game.action.skeleton.core.CmdInfo;
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
    ModuleInfoRegion externalModuleInfoRegion = new ModuleInfoRegion();

    public ModuleInfoProxy getModuleInfo(CmdInfo cmdInfo) {
        Integer moduleId = moduleIdMap.get(cmdInfo.getCmdMerge());

        ModuleInfoRegion moduleInfoRegion = moduleInfoRegionMap.get(moduleId);

        if (Objects.isNull(moduleInfoRegion)) {
            return null;
        }

        return moduleInfoRegion.getModuleInfo();
    }

    public ModuleInfoProxy getExternalModuleInfo() {
        return externalModuleInfoRegion.getModuleInfo();
    }


    public void addModuleInfo(ModuleMessage moduleMessage) {
        switch (moduleMessage.getModuleType()) {
            case LOGIC -> extractedLogic(moduleMessage);
            case EXTERNAL -> externalModuleInfoRegion.addModuleInfo(moduleMessage);
        }
    }

    private void extractedLogic(ModuleMessage moduleMessage) {
        var moduleId = moduleMessage.getModuleKey().moduleId;

        ModuleInfoRegion moduleInfoRegion = moduleInfoRegionMap.get(moduleId);

        // 无锁化
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
