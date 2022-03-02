package com.iohao.little.game.net.external.simple;

import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bolt.AbstractExternalClientStartupConfig;
import com.iohao.little.game.net.external.bootstrap.ExternalJoinEnum;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleType;
import com.iohao.little.game.net.server.core.ServerStartupConfig;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * 简单的快速启动工具： 对外服、网关服
 * <pre>
 *     注意：
 *          这个工具只适合单机的开发或本地一体化的开发, 对于分步式不适合。
 * </pre>
 *
 * @author 洛朱
 * @date 2022-02-24
 */
@UtilityClass
public class SimpleHelper {

    /**
     * 简单的快速启动
     * <pre>
     *     快速启动:
     *          对外服
     *          网关服
     *          逻辑服
     *
     *      包括游戏业务文档的生成
     * </pre>
     *
     * @param externalPort 游戏对外服端口
     * @param gatewayPort  游戏网关端口
     * @param logicList    逻辑服列表
     */
    public void run(int externalPort, int gatewayPort, List<ClientStartupConfig> logicList) {

        // 对外服
        ExternalServer externalServer = createExternalServer(externalPort, gatewayPort);

        // 网关服务器
        ServerStartupConfig gatewayServer = () -> new BoltServer(gatewayPort);

        // 简单的启动器
        new SimpleRunOne()
                // 网关服务器
                .setGatewayServer(gatewayServer)
                // 对外服
                .setExternalServer(externalServer)
                // 逻辑服列表
                .setLogicServerList(logicList)
                // 启动
                .startup();
    }

    private ExternalServer createExternalServer(int externalPort, int gatewayPort) {
        // 内部逻辑服 连接网关服务器
        var externalClientStartupConfig = new InternalExternalClientStartupConfig(gatewayPort);

        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(externalPort)
                // websocket 方式连接
                .setExternalJoinEnum(ExternalJoinEnum.WEBSOCKET)
                // 内部逻辑服 连接网关服务器
                .setExternalClientStartupConfig(externalClientStartupConfig);

        // 构建游戏对外服
        return builder.build();
    }


    static class InternalExternalClientStartupConfig extends AbstractExternalClientStartupConfig {
        /** 游戏网关端口 */
        int gatewayPort;

        public InternalExternalClientStartupConfig(int gatewayPort) {
            this.gatewayPort = gatewayPort;
        }

        @Override
        public ModuleMessage createModuleMessage() {
            int moduleId = 0;
            var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

            // 对外服模块信息
            return new ModuleMessage(moduleKey)
                    .setModuleType(ModuleType.EXTERNAL)
                    .setName("对外服务器(external)")
                    .setDescription("对接真实的游戏用户");
        }

        @Override
        public RemoteAddress createRemoteAddress() {
            // 游戏网关 ip， 实际开发中写你的游戏网关具体地址。
            String gatewayIp = "127.0.0.1";
            return new RemoteAddress(gatewayIp, gatewayPort);
        }
    }
}
