package com.iohao.little.game.net.message.common;

import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 模块key
 * <pre>
 *     子服务器的唯一标识
 *
 *     moduleId : 表示相同业务的模块
 *     sequenceId : 相同业务模块启动了多个服务器
 *     如： 天气预报模块，启动了N台服务器提供查询
 * </pre>
 */
@ToString
public class ModuleKey implements Serializable {
    /** 模块id */
    public final int moduleId;
    /** 序列 */
    public final int sequenceId;
    public final String unionId;

    ModuleKey(int moduleId, int sequenceId) {
        this.moduleId = moduleId;
        this.sequenceId = sequenceId;
        this.unionId = moduleId + "-" + sequenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleKey moduleKey = (ModuleKey) o;
        return Objects.equals(unionId, moduleKey.unionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unionId);
    }
}
