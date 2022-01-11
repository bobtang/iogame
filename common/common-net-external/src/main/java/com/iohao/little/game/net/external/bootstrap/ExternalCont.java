package com.iohao.little.game.net.external.bootstrap;

/**
 * @author 洛朱
 * @date 2022-01-10
 */
public interface ExternalCont {
    /** 2 + 1 + 1 + 4 + 2 + 4 */
    int HEADER_LEN = 14;

    /** 请求命令类型: 0 心跳 */
    int PROTOCOL_HEART = 0;

    /** 请求命令类型: 1 业务 */
    int PROTOCOL_BIZ = 1;

}
