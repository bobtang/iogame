package com.iohao.little.game.net.message.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

/**
 * 子模块信息 （子游戏服的信息、逻辑服）
 *
 * @author 洛朱
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModuleMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -1570849960266785140L;
    /** 模块key */
    ModuleKey moduleKey;
    /** 模块名 */
    String name;
    /** 描述 */
    String description;
    /** 逻辑服地址 */
    String address;
    int[] cmdMergeArray;
    /** 逻辑服类型 */
    ModuleType moduleType = ModuleType.LOGIC;

    public ModuleMessage(ModuleKey moduleKey) {
        this.moduleKey = moduleKey;
    }

    public ModuleMessage() {
    }

    @Override
    public String toString() {
        return "ModuleMessage{" +
                "moduleKey=" + moduleKey +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", moduleType='" + moduleType + '\'' +
                '}';
    }
}
