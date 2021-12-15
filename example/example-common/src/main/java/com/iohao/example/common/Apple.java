package com.iohao.example.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Apple implements Serializable {
    int id;
    String name;
    int age;

}
