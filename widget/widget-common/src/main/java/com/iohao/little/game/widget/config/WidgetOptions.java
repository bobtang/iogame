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

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置项管理器
 */
public class WidgetOptions {

    @Getter
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
