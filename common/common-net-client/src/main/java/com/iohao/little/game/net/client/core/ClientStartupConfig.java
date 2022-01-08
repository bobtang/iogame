package com.iohao.little.game.net.client.core;

import com.alipay.remoting.rpc.RpcClient;
import com.iohao.little.game.action.skeleton.core.ActionCommandManager;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.client.BoltClientServer;
import com.iohao.little.game.net.client.BoltClientServerSetting;
import com.iohao.little.game.net.client.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * client server 启动流程
 *
 * @author 洛朱
 * @Date 2021-12-17
 */
public interface ClientStartupConfig {
    /**
     * 初始化 业务框架
     *
     * @return 业务框架
     */
    BarSkeleton createBarSkeleton();

    /**
     * 初始化 模块信息
     *
     * @return 模块信息
     */
    ModuleMessage createModuleMessage();

    /**
     * 初始化 远程连接地址
     *
     * @return 远程连接地址
     */
    RemoteAddress createRemoteAddress();

    /**
     * 构建配置项
     *
     * @param setting 客户端服务器构建配置项
     */
    void serverSetting(BoltClientServerSetting setting);

    /**
     * 添加连接处理器
     * <pre>
     *     see:
     *     {@link com.alipay.remoting.ConnectionEventType#CLOSE}
     *     {@link com.alipay.remoting.ConnectionEventType#CONNECT}
     * </pre>
     *
     * @param boltClientServer boltClientServer
     */
    void connectionEventProcessor(BoltClientServer boltClientServer);

    /**
     * 注册用户处理器
     *
     * @param boltClientServer boltClientServer
     */
    void registerUserProcessor(BoltClientServer boltClientServer);

    /**
     * 部件扩展 可以通过部件的方式来扩展
     *
     * @param widgetComponents 部件
     */
    void widgetComponents(WidgetComponents widgetComponents);

    /**
     * 启动
     * <pre>
     *     模板方法模式
     * </pre>
     */
    default void startup() {
        // 业务框架
        BarSkeleton barSkeleton = createBarSkeleton();

        // 创建模块信息
        ModuleMessage moduleMessage = this.createModuleMessage();
        // 设置模块包含的 cmd 列表
        ActionCommandManager actionCommandManager = barSkeleton.getActionCommandManager();
        int[] cmdMergeArray = actionCommandManager.arrayCmdMerge();
        moduleMessage.setCmdMergeArray(cmdMergeArray);

        // 远程连接地址
        RemoteAddress remoteAddress = this.createRemoteAddress();

        // 服务器设置
        BoltClientServerSetting setting = new BoltClientServerSetting(barSkeleton, moduleMessage, remoteAddress);
        // 构建配置项
        this.serverSetting(setting);

        // 构建客户端服务器
        BoltClientServer boltClientServer = new BoltClientServer(setting);

        // 添加连接处理器
        this.connectionEventProcessor(boltClientServer);
        // 注册用户处理器
        this.registerUserProcessor(boltClientServer);

        // 初始化 客户端服务器
        boltClientServer.init();

        // 客户端服务器代理，
        BoltClientProxy boltClientProxy = setting.getBoltClientProxy();

        // 部件组成部份 - 扩展功能可以通过部件的方式来扩展
        WidgetComponents widgetComponents = boltClientProxy.getWidgetComponents();
        widgetComponents(widgetComponents);

        // 启动 rpc client
        RpcClient rpcClient = boltClientServer.getRpcClient();
        rpcClient.startup();

        // 客户端服务器注册到网关服
        boltClientServer.registerModuleToGate();


    }
}
