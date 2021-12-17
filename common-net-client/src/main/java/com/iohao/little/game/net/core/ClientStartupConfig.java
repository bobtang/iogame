package com.iohao.little.game.net.core;

import com.iohao.little.game.action.skeleton.core.ActionCommandManager;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.net.BoltClientServer;
import com.iohao.little.game.net.BoltClientServerSetting;
import com.iohao.little.game.net.common.BoltClientProxy;
import com.iohao.little.game.net.message.common.ModuleMessage;
import com.iohao.little.game.widget.config.WidgetComponents;

/**
 * client server 启动流程
 *
 * @author 洛朱
 * @date 2021/12/17
 */
public interface ClientStartupConfig {

    /**
     * 初始化 远程连接地址
     *
     * @return 远程连接地址
     */
    RemoteAddress remoteAddress();

    /**
     * 初始化 业务框架
     *
     * @return 业务框架
     */
    BarSkeleton iniBarSkeleton();

    /**
     * 初始化 模块信息
     *
     * @return 模块信息
     */
    ModuleMessage createModuleMessage();

    /**
     * 构建配置项
     *
     * @param setting 客户端服务器构建配置项
     */
    void serverSetting(BoltClientServerSetting setting);

    /**
     * 部件扩展 可以通过部件的方式来扩展
     *
     * @param widgetComponents 部件
     */
    void widgetComponents(WidgetComponents widgetComponents);

    /**
     * 启动
     */
    default void startup() {
        // 业务框架
        BarSkeleton barSkeleton = iniBarSkeleton();

        // 创建模块信息
        ActionCommandManager actionCommandManager = barSkeleton.getActionCommandManager();
        int[] cmdMergeArray = actionCommandManager.arrayCmdMerge();
        // 设置模块包含的 cmd 列表
        ModuleMessage moduleMessage = createModuleMessage();
        moduleMessage.setCmdMergeArray(cmdMergeArray);
        System.out.println(moduleMessage);

        // 远程连接地址
        RemoteAddress remoteAddress = remoteAddress();

        // 服务器设置
        BoltClientServerSetting setting = new BoltClientServerSetting(barSkeleton, moduleMessage, remoteAddress);
        // 构建配置项
        serverSetting(setting);

        // 构建客户端服务器
        BoltClientServer boltClientServer = setting.build();
        // 初始化 客户端服务器
        boltClientServer.init();
        // 客户端服务器注册到网关服
        boltClientServer.registerModuleToGate();

        // 客户端服务器代理，
        BoltClientProxy boltClientProxy = setting.getBoltClientProxy();

        // 部件组成部份 - 扩展功能可以通过部件的方式来扩展
        WidgetComponents widgetComponents = boltClientProxy.getWidgetComponents();
        widgetComponents(widgetComponents);
    }
}
