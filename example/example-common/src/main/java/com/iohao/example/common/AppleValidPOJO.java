package com.iohao.example.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
@Data
public class AppleValidPOJO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3905659585818706257L;

    @NotNull
    @Email
    String email;

    @Min(value = 2, message = "年龄不能小于 2")
    int age;
}
