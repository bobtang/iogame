package com.iohao.little.game.common.kit.asm;

import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Method;

/**
 * 类方法信息
 *
 * @author 洛朱
 * @date 2022-01-02
 */
@Getter
@FieldDefaults(level = AccessLevel.PACKAGE)
public class MethodRefInfo {

    /** 方法下标 */
    int methodIndex;
    /** 方法名 */
    String methodName;
    /** 原生方法对象 */
    Method method;
    /** 方法 访问器 */
    MethodAccess methodAccess;

    MethodRefInfo() {

    }

    /**
     * 执行方法
     *
     * @param object 业务对象
     * @param args   方法参数
     * @return 返回值
     */
    public Object invokeMethod(Object object, Object args) {
        return methodAccess.invoke(object, methodIndex, args);
    }

    /**
     * 执行无参方法
     *
     * @param object 业务对象
     * @return 返回值
     */
    public Object invokeMethod(Object object) {
        return methodAccess.invoke(object, methodIndex);
    }
}
