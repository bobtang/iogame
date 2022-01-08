package com.iohao.little.game.net.message.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * 子模块信息 （子游戏服的信息）
 */
@Getter
@Setter
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

    @Override
    public String toString() {
        return "ModuleMessage{" +
                "moduleKey=" + moduleKey +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
