package com.iohao.little.game.net.external.simple;

import com.iohao.little.game.action.skeleton.core.doc.BarSkeletonDoc;
import com.iohao.little.game.common.kit.ExecutorKit;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.external.ExternalServerBuilder;
import com.iohao.little.game.net.external.bolt.AbstractExternalClientStartupConfig;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.net.message.common.ModuleType;
import com.iohao.little.game.net.server.core.ServerStartupConfig;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    ScheduledExecutorService executorService = ExecutorKit.newScheduled(4, "SimpleHelper");

    /**
     * 简单的快速启动
     * <pre>
     *     快速启动:
     *          对外服
     *          网关服
     *          逻辑服
     * </pre>
     *
     * @param port        游戏对外服端口
     * @param gatewayPort 游戏网关端口
     * @param logicList   逻辑服列表
     */
    public void run(int port, int gatewayPort, List<ClientStartupConfig> logicList) {
        // 启动网关
        startupGateway(gatewayPort);

        executorService.schedule(() -> {

            // 启动逻辑服
            logicList.forEach(ClientStartupConfig::startup);

            // 启动对外服
            startupExternal(port, gatewayPort);

            // 生成游戏文档
            BarSkeletonDoc.me().buildDoc();

        }, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startupExternal(int port, int gatewayPort) {
        // 启动内部逻辑服 连接网关服务器
        executorService.execute(() -> {
            new InternalExternalClientStartupConfig(gatewayPort).startup();
            System.out.println("external 启动内部逻辑服, 用于连接网关服务器");
        });

        // 对外服连接网关服务器

        // 游戏对外服 - 构建器
        ExternalServerBuilder builder = ExternalServer.newBuilder(port);
        // 构建游戏对外服
        ExternalServer externalServer = builder.build();
        // 启动游戏对外服
        externalServer.startup();

        System.out.println("external 启动游戏对外服 !");

    }

    private void startupGateway(int port) {

        executorService.execute(() -> {
            ServerStartupConfig gateway = () -> new BoltServer(port);
            gateway.startup();
            System.out.println("网关启动 ok!");
        });
    }

    static class InternalExternalClientStartupConfig extends AbstractExternalClientStartupConfig {
        int port;

        public InternalExternalClientStartupConfig(int port) {
            this.port = port;
        }

        @Override
        public ModuleMessage createModuleMessage() {
            int moduleId = 0;
            var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

            ModuleMessage moduleMessage = new ModuleMessage();
            moduleMessage.setModuleType(ModuleType.EXTERNAL);
            moduleMessage.setModuleKey(moduleKey);
            moduleMessage.setName("对外服务器(external)");
            moduleMessage.setDescription("对接真实的游戏用户");

            return moduleMessage;
        }

        @Override
        public RemoteAddress createRemoteAddress() {
            String ip = "127.0.0.1";
            return new RemoteAddress(ip, port);
        }
    }
}
