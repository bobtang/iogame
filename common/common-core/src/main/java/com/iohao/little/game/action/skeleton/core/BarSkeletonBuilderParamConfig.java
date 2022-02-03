package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.DocActionSends;
import com.iohao.little.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.function.Predicate;

/**
 * BarSkeletonBuilderParamConfig 构建参数的配置
 * <pre>
 *     设置一些参数
 * </pre>
 *
 * @author 洛朱
 * @date 2022-02-01
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarSkeletonBuilderParamConfig {
    /** action controller class. class has @ActionController */
    final List<Class<?>> actionControllerClassList = new ArrayList<>();
    /** action send class. class has @DocActionSend */
    final List<Class<?>> actionSendClassList = new ArrayList<>();
    /** 错误码 class */
    final List<MsgExceptionInfo> msgExceptionInfoList = new ArrayList<>();

    /** ActionController filter */
    Predicate<Class<?>> actionControllerPredicateFilter = (clazz) -> Objects.nonNull(clazz.getAnnotation(ActionController.class));
    /** 推送相关的 class */
    Predicate<Class<?>> actionSendPredicateFilter = (clazz) -> Objects.nonNull(clazz.getAnnotation(DocActionSends.class));

    public BarSkeletonBuilderParamConfig addActionController(Class<?> actionControllerClass) {
        this.actionControllerClassList.add(actionControllerClass);
        return this;
    }

    public BarSkeletonBuilderParamConfig addActionSend(Class<?> actionSendClass) {
        this.actionSendClassList.add(actionSendClass);
        return this;
    }

    public BarSkeletonBuilderParamConfig addErrorCode(MsgExceptionInfo[] msgExceptionInfoArray) {
        msgExceptionInfoList.addAll(Arrays.asList(msgExceptionInfoArray));
        return this;
    }

}
