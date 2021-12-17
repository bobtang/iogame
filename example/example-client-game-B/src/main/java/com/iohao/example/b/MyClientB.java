package com.iohao.example.b;

public class MyClientB {

    public static void main(String[] args) throws InterruptedException {
        MyClientB clientB = new MyClientB();
        clientB.init();

        Thread.sleep(1000 * 1200);
    }

    public void init() {
        MyClientBClientStartupConfig config = new MyClientBClientStartupConfig();
        config.startup();
    }
}
