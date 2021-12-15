package com.iohao.little.game.net.message.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * 子模块信息 （子游戏服的信息）
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModuleMessage implements Serializable {
    ModuleKey moduleKey;
    String name;
    String description;
    String address;
    int[] cmdMergeArray;

    public void setModuleKey(ModuleKey moduleKey) {
        this.moduleKey = moduleKey;
    }
}
