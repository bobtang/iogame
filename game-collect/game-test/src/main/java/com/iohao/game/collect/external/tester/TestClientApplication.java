package com.iohao.game.collect.external.tester;

import com.iohao.game.collect.external.tester.websocket.MyWebsocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 洛朱
 * @date 2022-01-29
 */
@Slf4j
@SpringBootApplication
public class TestClientApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestClientApplication.class, args);
        System.out.println();

        // 启动客户端
        MyWebsocketClient.me().start();

    }
}
