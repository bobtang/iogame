package com.iohao.example.a;

public class MyClientA {

    public static void main(String[] args) throws Exception {
        MyClientA clientA = new MyClientA();
        clientA.init();
        Thread.sleep(1000 * 1200);
    }

    public void init() {
        MyClientAClientStartupConfig config = new MyClientAClientStartupConfig();
        config.startup();
    }
}
