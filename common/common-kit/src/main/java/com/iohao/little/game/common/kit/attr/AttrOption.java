package com.iohao.little.game.common.kit.attr;

import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-31
 */
public record AttrOption<T>(String name, T defaultValue) {

    public static <T> AttrOption<T> valueOf(String name) {
        return new AttrOption<T>(name, null);
    }

    public static <T> AttrOption<T> valueOf(String name, T defaultValue) {
        return new AttrOption<T>(name, defaultValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttrOption<?> that = (AttrOption<?>) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
