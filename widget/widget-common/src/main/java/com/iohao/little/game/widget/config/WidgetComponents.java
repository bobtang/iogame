package com.iohao.little.game.widget.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 部件管理器
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public class WidgetComponents {

    /**
     * map
     * <pre>
     *     key : WidgetComponent class
     *     value : WidgetComponent
     * </pre>
     */
    private final ConcurrentHashMap<Class<? extends WidgetComponent>, WidgetComponent> componentMap = new ConcurrentHashMap<>();

    /**
     * 添加部件
     *
     * @param name            部件名
     * @param widgetComponent 部件
     */
    public void option(Class<? extends WidgetComponent> name, WidgetComponent widgetComponent) {
        componentMap.put(name, widgetComponent);
    }

    /**
     * 获取部件 通过名字
     *
     * @param name 部件名
     * @param <T>  t
     * @return 部件
     */
    @SuppressWarnings("unchecked")
    public <T extends WidgetComponent> T option(Class<T> name) {
        return (T) componentMap.get(name);
    }
}
