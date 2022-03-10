/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.little.game.widget.config;

import lombok.ToString;

import java.util.Objects;

/**
 * 配置项
 *
 * @author 洛朱
 * @Date 2021-12-16
 */
@ToString
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