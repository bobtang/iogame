package com.iohao.little.game.common.kit.attr;

import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.Objects;

/**
 * 选项载体
 *
 * @author 洛朱
 * @date 2022-01-31
 */
public class AttrOptions {
    final Map<AttrOption<?>, Object> options = new NonBlockingHashMap<>();

    /**
     * 获取选项值。
     * <p>
     * 如果选项不存在，返回默认值。
     *
     * @param option 选项值
     * @return 如果option不存在，则默认的option值。
     */
    @SuppressWarnings("unchecked")
    public <T> T option(AttrOption<T> option) {
        Object value = options.get(option);
        if (Objects.isNull(value)) {
            value = option.defaultValue();
        }

        return value == null ? null : (T) value;
    }

    /**
     * 设置一个具有特定值的新选项。
     * <p>
     * 使用 null 值删除前一个设置的 {@link AttrOption}。
     *
     * @param option 选项值
     * @param value  选项值, null 用于删除前一个 {@link AttrOption}.
     * @return this
     */
    public <T> AttrOptions option(AttrOption<T> option, T value) {
        if (Objects.isNull(value)) {
            options.remove(option);
            return this;
        }

        options.put(option, value);
        return this;
    }
}
