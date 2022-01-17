package com.iohao.little.game.common.kit;

import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * 基础类型相关工具
 *
 * @author 洛朱
 * @date 2022-01-16
 */
@UtilityClass
public class BaseTypeKit {
    /** 基础类型 */
    private Set<Class<?>> baseTypeSet = Set.of(
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Character.class,
            Double.class,
            Float.class,
            String.class
    );


    /**
     * 验证该对象是否基础类型
     * <pre>
     *     基础类型包括:
     *             Byte.class,
     *             Short.class,
     *             Integer.class,
     *             Long.class,
     *             Character.class,
     *             Double.class,
     *             Float.class,
     *             String.class
     * </pre>
     *
     * @param value 验证对象
     * @return true 是基础类型
     */
    public boolean isBaseType(Object value) {
        Class<?> beanClass = value.getClass();
        return baseTypeSet.contains(beanClass);
    }

    /**
     * 验证该对象是否基础类型
     * <pre>
     *     基础类型包括:
     *             Byte.class,
     *             Short.class,
     *             Integer.class,
     *             Long.class,
     *             Character.class,
     *             Double.class,
     *             Float.class,
     *             String.class
     * </pre>
     *
     * @param beanClass 验证 class
     * @return true 是基础类型
     */
    public boolean isBaseType(Class<?> beanClass) {
        return baseTypeSet.contains(beanClass);
    }
}
