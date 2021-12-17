package com.iohao.example.common.md;

import lombok.Data;

import java.io.Serializable;

@Data
public class Active implements Serializable {
    int id;
    String name;
    int age;

}
