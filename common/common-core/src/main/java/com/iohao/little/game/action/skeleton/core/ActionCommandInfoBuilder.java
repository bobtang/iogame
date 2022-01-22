package com.iohao.little.game.action.skeleton.core;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.common.kit.ClassScanner;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
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

    private final BarSkeletonSetting setting;

    private final Map<String, JavaClassDocInfo> javaClassSourceMap = new ConcurrentHashMap<>();

    ActionCommandInfoBuilder(BarSkeletonSetting setting) {
        this.setting = setting;
    }

    void clean() {
        map.clear();
        javaClassSourceMap.clear();
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
        // java source
        javaProjectBuilder(controllerList);

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

            JavaClassDocInfo javaClassDocInfo = javaClassSourceMap.get(controllerClazz.toString());

            // 遍历所有方法上有 ActionMethod 注解的方法对象
            BarInternalKit.getMethodStream(controllerClazz).forEach(method -> {

                ActionCommandDoc actionCommandDoc = createActionCommandDoc(javaClassDocInfo, method);

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
                        .setReturnTypeClazz(returnType)
                        .setActionCommandDoc(actionCommandDoc);

                // 检测路由是否重复
                checkExistSubCmd(controllerClazz, subCmd, subActionMap);

                // 方法参数信息
                paramInfo(method, builder);

                // JSR
                ValidatorKit.buildValidator(this.setting, builder);

                /*
                路由key，根据这个路由可以找到对应的 command（命令对象）
                将映射类的方法，保存在 command 中。每个command封装成一个命令对象。
                 */
                var command = builder.build(this.setting);

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

            // 方法的参数下标
            paramInfo.index = i;
            // 方法的参数名
            paramInfo.name = p.getName();
            // 方法的参数类型 class
            paramInfo.paramClazz = p.getType();

        }
    }

    private void checkExistSubCmd(Class<?> controllerClass, int subCmd, Map<Integer, ActionCommand> subActionMap) {
        if (subActionMap.containsKey(subCmd)) {
            String message = StrUtil.format("已经存在方法编号:{} : {} .请查看: {}", subCmd, controllerClass);
            throw new RuntimeException(message);
        }
    }

    private ActionCommandDoc createActionCommandDoc(JavaClassDocInfo javaClassDocInfo, Method method) {

        if (Objects.isNull(javaClassDocInfo)) {
            return new ActionCommandDoc();
        }

        return javaClassDocInfo.createActionCommandDoc(method);
    }

    private void javaProjectBuilder(List<Class<?>> controllerList) {
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();

        for (Class<?> actionClazz : controllerList) {

            try {
                String packagePath = actionClazz.getPackageName();
                ClassScanner classScanner = new ClassScanner(packagePath, null);
                List<URL> resources = classScanner.getResources();

                for (URL resource : resources) {

                    String path = resource.getPath();
                    String srcPath = path.replace("target/classes", "src/main/java");

                    File file = new File(srcPath);
                    if (!FileUtil.exist(file)) {
                        continue;
                    }

                    javaProjectBuilder.addSourceTree(file);
                }

                Collection<JavaClass> classes = javaProjectBuilder.getClasses();

                for (JavaClass javaClass : classes) {

                    JavaClassDocInfo javaClassDocInfo = new JavaClassDocInfo();
                    javaClassDocInfo.setJavaClass(javaClass);

                    javaClassSourceMap.put(javaClass.toString(), javaClassDocInfo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class JavaClassDocInfo {
        JavaClass javaClass;
        Map<String, JavaMethod> javaMethodMap = new HashMap<>();

        void setJavaClass(JavaClass javaClass) {
            this.javaClass = javaClass;
            List<JavaMethod> methods = javaClass.getMethods();
            for (JavaMethod method : methods) {
                javaMethodMap.put(method.toString(), method);
            }
        }

        JavaMethod getJavaMethod(Method method) {
            return javaMethodMap.get(method.toString());
        }

        ActionCommandDoc createActionCommandDoc(Method method) {
            JavaMethod javaMethod = getJavaMethod(method);

            ActionCommandDoc actionCommandDoc = new ActionCommandDoc();

            actionCommandDoc.classComment = this.javaClass.getComment();
            actionCommandDoc.classLineNumber = this.javaClass.getLineNumber();
            actionCommandDoc.comment = javaMethod.getComment();
            actionCommandDoc.lineNumber = javaMethod.getLineNumber();

            if (actionCommandDoc.classComment == null) {
                actionCommandDoc.classComment = "";
            }

            if (actionCommandDoc.comment == null) {
                actionCommandDoc.comment = "";
            }

            return actionCommandDoc;
        }
    }

}
