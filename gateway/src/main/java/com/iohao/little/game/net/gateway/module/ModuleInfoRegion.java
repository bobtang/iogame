package com.iohao.little.game.net.gateway.module;

import com.iohao.little.game.net.message.common.ModuleMessage;

import java.util.*;

public class ModuleInfoRegion {
    Set<ModuleInfoProxy> moduleInfoProxySet = new HashSet<>();
    int moduleId;
    ModuleInfoProxy moduleInfoProxy;
    public void addModuleInfo(ModuleMessage moduleMessage) {
        moduleInfoProxySet.clear();

        ModuleInfoProxy moduleInfoProxy = new ModuleInfoProxy(moduleMessage);
        moduleInfoProxySet.add(moduleInfoProxy);

        this.moduleId = moduleMessage.getModuleKey().moduleId;
        this.moduleInfoProxy = moduleInfoProxy;
    }

    public ModuleInfoProxy getModuleInfo() {
        if (moduleInfoProxySet.isEmpty()) {
            return null;
        }

        return moduleInfoProxy;
//        return moduleInfoProxySet.stream().findAny().get();
    }
}
