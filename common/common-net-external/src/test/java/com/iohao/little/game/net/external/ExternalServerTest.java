package com.iohao.little.game.net.external;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 洛朱
 * @date 2022-01-09
 */
public class ExternalServerTest {

    @Test
    public void startup() {
        int port = 22022;

        ExternalServerBuilder builder = ExternalServer.newBuilder(port);

        ExternalServer externalServer = builder.build();

        externalServer.startup();
        System.out.println("OK!");


    }
}