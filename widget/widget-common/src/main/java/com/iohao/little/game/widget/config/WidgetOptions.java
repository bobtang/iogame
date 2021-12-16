package com.iohao.little.game.widget.config;

import java.util.concurrent.ConcurrentHashMap;

public class WidgetOptions {

    private final ConcurrentHashMap<WidgetOption<?>, Object> options = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T option(WidgetOption<T> option) {
        Object value = options.get(option);
        if (value == null) {
            value = option.defaultValue();
        }

        return value == null ? null : (T) value;
    }


    public <T> WidgetOptions option(WidgetOption<T> option, T value) {
        if (value == null) {
            options.remove(option);
            return this;
        }

        options.put(option, value);
        return this;
    }
}