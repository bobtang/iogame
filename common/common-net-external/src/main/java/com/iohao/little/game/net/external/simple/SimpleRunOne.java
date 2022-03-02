package com.iohao.little.game.net.external.simple;

import com.iohao.little.game.action.skeleton.core.doc.BarSkeletonDoc;
import com.iohao.little.game.common.kit.ExecutorKit;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.external.ExternalServer;
import com.iohao.little.game.net.server.core.ServerStartupConfig;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 简单的启动器： 对外服、网关服、逻辑服
 * <pre>
 *     注意：
 *          这个工具只适合单机的开发或本地一体化的开发, 对于分步式不适合。
 * </pre>
 *
 * @author 洛朱
 * @date 2022-02-28
 */
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleRunOne {
    final ScheduledExecutorService executorService = ExecutorKit.newScheduled(2, SimpleRunOne.class.getSimpleName());

    /** 网关 */
    ServerStartupConfig gatewayServer;
    /** 对外服 */
    ExternalServer externalServer;
    /** 逻辑服 */
    List<ClientStartupConfig> logicServerList;

    /**
     * 简单的快速启动
     * <pre>
     *     快速启动:
     *          对外服
     *          网关服
     *          逻辑服
     * </pre>
     */
    public void startup() {
        // 启动网关
        executorService.execute(() -> {
            gatewayServer.startup();
            System.out.println("网关启动 ok!");
        });

        // 启动逻辑服、对外服
        startupLogic();
    }

    private void startupLogic() {
        executorService.schedule(() -> {

            // 启动逻辑服
            logicServerList.forEach(ClientStartupConfig::startup);

            // 启动游戏对外服
            externalServer.startup();

            System.out.println("external 启动游戏对外服 !");

            // 生成游戏文档
            BarSkeletonDoc.me().buildDoc();

        }, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
