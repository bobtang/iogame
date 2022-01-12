package com.iohao.little.game.net.server.core;

import com.iohao.little.game.net.common.BoltServer;
import com.iohao.little.game.net.server.GateKit;
import com.iohao.little.game.widget.broadcast.internal.GateBroadcastWidgetComponents;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * 服务端启动配置
 * <pre>
 *     一般指网关服
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public interface ServerStartupConfig {

    /**
     * 创建 BoltServer
     *
     * @return BoltServer
     */
    BoltServer createBoltServer();

    /**
     * 部件扩展 可以通过部件的方式来扩展
     *
     * @param widgetComponents 部件
     */
    default void widgetComponents(WidgetComponents widgetComponents) {
        GateBroadcastWidgetComponents.configBroadcastWidgetComponents(widgetComponents);
    }

    /**
     * 添加连接处理器
     * <pre>
     *     see:
     *     {@link com.alipay.remoting.ConnectionEventType#CLOSE}
     *     {@link com.alipay.remoting.ConnectionEventType#CONNECT}
     * </pre>
     *
     * @param boltServer boltServer
     */
    void connectionEventProcessor(BoltServer boltServer);

    /**
     * 注册用户处理器
     *
     * @param boltServer boltServer
     */
    void registerUserProcessor(BoltServer boltServer);

    /**
     * 服务器启动前的钩子方法
     *
     * @param boltServer boltServer
     */
    default void startBefore(BoltServer boltServer) {

    }

    /**
     * 启动
     * <pre>
     *     模板方法模式
     * </pre>
     */
    default void startup() {
        // 创建 BoltServer
        BoltServer boltServer = createBoltServer();
        GateKit.boltServer = boltServer;

        // 部件扩展 可以通过部件的方式来扩展
        WidgetComponents widgetComponents = boltServer.getWidgetComponents();
        widgetComponents(widgetComponents);

        // 添加连接处理器
        connectionEventProcessor(boltServer);

        // 注册用户处理器
        registerUserProcessor(boltServer);

        // 服务器启动前的钩子方法
        startBefore(boltServer);

        // 启动服务端
        boltServer.start();
    }
}
