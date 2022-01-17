package com.iohao.example.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Apple implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905284340375312367L;
    int id;
    String name;
    int age;

}
