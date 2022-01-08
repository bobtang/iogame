package com.iohao.little.game.common.kit.asm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 字段信息 - 对应类的成员属性
 * <pre>
 *    name
 *    age
 * </pre>
 * <pre>
 *     不能提供私有属性的直接方法
 *     属性必须拥有 getter setter
 *     如果要调用私有属性, 使用 Field
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-02
 */
@ToString
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class FieldRefInfo implements Serializable {
    private static final long serialVersionUID = -5717006947739357125L;
    /** 字段名 */
    String fieldName;
    /** 返回值类型 */
    Class<?> returnType;
    /** 字段对应的 get 方法下标 */
    int methodGetIndex;
    /** 字段对应的 set 方法下标 */
    int methodSetIndex;
    /** 字段对应的 get 方法名 */
    String methodGetName;
    /** 字段对应的 set 方法名 */
    String methodSetName;
    /** 原生字段 */
    Field field;
}