package com.iohao.little.game.widget.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 部件管理器
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public class WidgetComponents {
    private final ConcurrentHashMap<Class<?>, WidgetComponent> componentMap = new ConcurrentHashMap<>();

    public void option(Class<?> name, WidgetComponent widgetComponent) {
        componentMap.put(name, widgetComponent);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> name) {
        return (T) componentMap.get(name);
    }
}
