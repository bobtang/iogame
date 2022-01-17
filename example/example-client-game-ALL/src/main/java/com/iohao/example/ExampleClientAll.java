package com.iohao.example;

import com.iohao.example.a.MyClientA;
import com.iohao.example.b.MyClientB;

public class ExampleClientAll {
    public static void main(String[] args) {
        MyClientA clientA = new MyClientA();
        clientA.init();

        MyClientB clientB = new MyClientB();
        clientB.init();
    }
}
