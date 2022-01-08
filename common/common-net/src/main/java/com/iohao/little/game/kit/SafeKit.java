package com.iohao.little.game.kit;

import java.util.Objects;

/**
 * 获取安全的值, 保证返回的不是 null
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class SafeKit {
    public static int getInt(Integer value) {
        return getInt(value, 0);
    }

    public static int getInt(Integer value, int defaultValue) {
        return Objects.isNull(value) ? defaultValue : value;
    }

    public static long getLong(Long value) {
        return getLong(value, 0);
    }

    public static long getLong(Long value, long defaultValue) {
        return Objects.isNull(value) ? defaultValue : value;
    }

    public static boolean getBoolean(Boolean value) {
        return getBoolean(value, false);
    }

    public static boolean getBoolean(Boolean value, boolean defaultValue) {
        return Objects.isNull(value) ? defaultValue : value;
    }
}
