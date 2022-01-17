package com.iohao.little.game.action.skeleton.core.action.pojo;

import com.iohao.little.game.action.skeleton.core.ValidatorKit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Set;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
@Slf4j
@Data
public class DogValid {
    @NotNull(message = "名字")
    private String name;

    @NotNull(message = "梦露")
    @Size(min = 2, max = 14)
    private String licensePlate;

    @Min(2)
    private int seatCount;

    int age;

    String nickname;

    @Test
    public void name() {
        DogValid dogValid = new DogValid();
        dogValid.setName("abc");
        Validator validator = ValidatorKit.getValidator();


        BeanDescriptor constraintsForClass = validator.getConstraintsForClass(DogValid.class);
        Set<PropertyDescriptor> constrainedProperties = constraintsForClass.getConstrainedProperties();
        log.info("c : {}", constraintsForClass);

        Set<ConstraintViolation<DogValid>> validate = validator.validate(dogValid);


        log.info("{}", validate.size());

        for (ConstraintViolation<DogValid> violation : validate) {
            log.info("{}", validate);
            String message = violation.getMessage();
            Path propertyPath = violation.getPropertyPath();

            log.info("message {}, path: {}", message, propertyPath.toString());
        }

    }
}
