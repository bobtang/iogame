package com.iohao.little.game.widget.config;

import java.util.Objects;

/**
 * 配置项
 *
 * @author 洛朱
 * @date 2021/12/16
 */
public class WidgetOption<T> {
    private final String name;
    private final T defaultValue;

    public String name() {
        return name;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public static <T> WidgetOption<T> valueOf(String name) {
        return new WidgetOption<T>(name, null);
    }

    public WidgetOption(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static <T> WidgetOption<T> valueOf(String name, T defaultValue) {
        return new WidgetOption<T>(name, defaultValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WidgetOption<?> that = (WidgetOption<?>) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}