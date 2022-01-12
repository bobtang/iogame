package com.iohao.game.collect.logic;

import com.iohao.game.collect.common.GameConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import lombok.experimental.UtilityClass;

/**
 * 游戏逻辑服通用配置
 * <pre>
 *     client rcp
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-12
 */
@UtilityClass
public class GameLogicCommonInit {
    public RemoteAddress createRemoteAddress() {
        String ip = GameConfig.ip;
        int port = GameConfig.port;
        return new RemoteAddress(ip, port);
    }

}
