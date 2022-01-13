package com.iohao.game.collect.common;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
public interface GameConfig {
    /** 对外服务器 port */
    int externalPort = 10088;
    /** 对外服务器 ip */
    String externalIp = "127.0.0.1";
    /** http 升级 websocket 协议地址 */
    String websocketPath = "/websocket";

    /** 网关 port */
    int gatePort = 10086;
    /** 网关 ip */
    String gateIp = "127.0.0.1";
}
