package com.iohao.little.game.action.skeleton.core;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.HibernateValidator;

import java.util.Objects;
import java.util.Set;


/**
 * 验证相关
 * <pre>
 *     符合 JSR-303、JSR 349。这里使用 hibernate-validator
 *
 *     主要用户验证业务参数
 *
 *     使用验证时需自行引入 hibernate-validator 包
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-16
 */
@Setter
@UtilityClass
public class ValidatorKit {

    private Validator validator;

    public Validator getValidator() {

        if (Objects.nonNull(validator)) {
            return validator;
        }

        ValidatorFactory validatorFactory = Validation
                .byProvider(HibernateValidator.class)
                .configure()
                // true  快速失败返回模式    false 普通模式
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        validator = validatorFactory.getValidator();
        return validator;
    }

    public String validate(Object data) {
        // 验证参数
        Set<ConstraintViolation<Object>> violationSet = getValidator().validate(data);
        if (violationSet.isEmpty()) {
            return null;
        }

        for (ConstraintViolation<Object> violation : violationSet) {
            return violation.getMessage();
        }

        return null;
    }

    /**
     * 过滤不需要验证的参数
     *
     * @param paramInfo paramInfo
     * @return true 不需要验证
     */
    private boolean filterParamClazz(ActionCommand.ParamInfo paramInfo) {
        Class<?> paramClazz = paramInfo.getParamClazz();
        return BizContext.class.equals(paramClazz)
                || AsyncContext.class.equals(paramClazz)
                || ServerContext.class.equals(paramClazz)
                || CmdInfo.class.equals(paramClazz)
                || RequestMessage.class.equals(paramClazz)
                || "userId".equals(paramInfo.getName());
    }

    /**
     * 业务方法参数验证
     * <pre>
     *     提前查看参数是否需要验证
     *     如果需要验证的，做个标记
     * </pre>
     *
     * @param setting setting
     * @param builder builder
     */
    void buildValidator(BarSkeletonSetting setting, ActionCommand.Builder builder) {
        if (!setting.validator) {
            // 没开启 JSR303、JSR 349 验证， 不做处理
            return;
        }

        ActionCommand.ParamInfo[] paramInfos = builder.paramInfos;

        if (Objects.isNull(paramInfos) || paramInfos.length == 0) {
            // 方法上没有参数，不做处理
            return;
        }

        for (ActionCommand.ParamInfo paramInfo : paramInfos) {

            if (ValidatorKit.filterParamClazz(paramInfo)) {
                // 过滤不需要验证的参数
                continue;
            }

            Class<?> paramClazz = paramInfo.paramClazz;

            // 根据 class 得到 bean 描述
            BeanDescriptor beanDescriptor = getValidator().getConstraintsForClass(paramClazz);
            // bean 的属性上添加的验证注解信息
            Set<PropertyDescriptor> descriptorSet = beanDescriptor.getConstrainedProperties();

            if (descriptorSet.isEmpty()) {
                // 表示这是一个不需要验证的参数
                continue;
            }

            // true 这是一个需要验证的参数
            paramInfo.validator = true;
        }
    }
}
