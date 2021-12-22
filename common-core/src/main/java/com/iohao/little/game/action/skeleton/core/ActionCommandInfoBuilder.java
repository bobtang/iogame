package com.iohao.little.game.action.skeleton.core;


import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * action命令对象的映射管理, 目前并没有做重复映射检测
 * <pre>
 *     实现方式是获取需要对外公开的 tcpActionController
 *     然后获取public void的方法,将这类型的方法封装成独立命令, 在与map进行一个简单的映射关系
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
public final class ActionCommandInfoBuilder {

    @Getter
    private final ConcurrentHashMap<Integer, Map<Integer, ActionCommand>> map = new ConcurrentHashMap<>();

    private final BarSkeletonSetting barSkeletonSetting;

    ActionCommandInfoBuilder(BarSkeletonSetting barSkeletonSetting) {
        this.barSkeletonSetting = barSkeletonSetting;
    }

    private Map<Integer, ActionCommand> getSubCmdMap(int cmd) {
        var subActionMap = map.get(cmd);

        // 无锁理念
        if (Objects.isNull(subActionMap)) {
            subActionMap = new ConcurrentHashMap<>();
            subActionMap = map.putIfAbsent(cmd, subActionMap);
            if (Objects.isNull(subActionMap)) {
                subActionMap = map.get(cmd);
            }
        }

        return subActionMap;
    }

    /**
     * 构建映射
     * <pre>
     *     这个方法可以做一个抽象接口
     *     让接口子类来处理如何生成 Map<Integer, Map<Integer, ActionCommand>> 的数据
     *     但现在不着急; 2021-12-21
     * </pre>
     *
     * @param controllerList action 类列表
     */
    ActionCommandInfoBuilder buildAction(List<Class<?>> controllerList) {
        Set<Class<?>> controllerSet = new HashSet<>(controllerList);

        // 条件: 类上配置了 ActionController 注解
        Predicate<Class<?>> controllerPredicate = controllerClazz -> Objects.nonNull(controllerClazz.getAnnotation(ActionController.class));

        controllerSet.stream().filter(controllerPredicate).forEach(controllerClazz -> {
            // 方法访问器: 获取类中自己定义的方法
            var methodAccess = MethodAccess.get(controllerClazz);
            var constructorAccess = ConstructorAccess.get(controllerClazz);

            // 主路由 (类上的路由)
            int cmd = controllerClazz.getAnnotation(ActionController.class).value();
            // 子路由 map
            var subActionMap = getSubCmdMap(cmd);

            // 遍历所有方法上有 ActionMethod 注解的方法对象
            BarInternalKit.getMethodStream(controllerClazz).forEach(method -> {
                // 目标子路由 (方法上的路由)
                int subCmd = method.getAnnotation(ActionMethod.class).value();
                // 方法名
                String methodName = method.getName();
                // 方法下标
                int methodIndex = methodAccess.getIndex(methodName);
                // 方法返回值类型
                Class<?> returnType = methodAccess.getReturnTypes()[methodIndex];

                // 新建一个命令构建器
                var builder = new ActionCommand.Builder()
                        .setCmd(cmd)
                        .setSubCmd(subCmd)
                        .setActionControllerClazz(controllerClazz)
                        .setActionControllerConstructorAccess(constructorAccess)
                        .setActionMethod(method)
                        .setActionMethodName(methodName)
                        .setActionMethodIndex(methodIndex)
                        .setActionMethodAccess(methodAccess)
                        .setReturnTypeClazz(returnType);

                // 方法参数信息
                paramInfo(method, builder);

                // 检测路由是否重复
                checkExistSubCmd(controllerClazz, subCmd, subActionMap);

                /*
                路由key，根据这个路由可以找到对应的 command（命令对象）
                将映射类的方法，保存在 command 中。每个command封装成一个命令对象。
                 */
                var command = builder.build(this.barSkeletonSetting);

                // 子路由映射
                subActionMap.put(subCmd, command);
            });

        });

        return this;
    }


    private void paramInfo(Method method, ActionCommand.Builder builder) {

        // 方法参数列表
        var parameters = method.getParameters();
        if (Objects.isNull(parameters)) {
            return;
        }

        var len = parameters.length;
        var paramInfos = new ActionCommand.ParamInfo[len];
        builder.setParamInfos(paramInfos);

        String name;
        for (int i = 0; i < len; i++) {
            // 构建参数信息
            var paramInfo = new ActionCommand.ParamInfo();
            paramInfos[i] = paramInfo;

            // 方法的参数对象
            Parameter p = parameters[i];
            // 方法的参数类型
            Class<?> type = p.getType();
            // 方法名
            name = p.getName();

            paramInfo.index = i;
            paramInfo.name = name;
            paramInfo.paramClazz = type;

        }
    }

    private void checkExistSubCmd(Class<?> controllerClass, int subCmd, Map<Integer, ActionCommand> subActionMap) {
        if (subActionMap.containsKey(subCmd)) {
            String message = StrUtil.format("已经存在方法编号:{} : {} .请查看: {}", subCmd, controllerClass);
            throw new RuntimeException(message);
        }
    }
}
