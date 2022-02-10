package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.doc.ActionSendDocs;
import com.iohao.little.game.action.skeleton.core.doc.BarSkeletonDoc;
import com.iohao.little.game.action.skeleton.core.doc.ErrorCodeDocs;
import com.iohao.little.game.action.skeleton.core.exception.MsgExceptionInfo;
import com.iohao.little.game.action.skeleton.core.flow.*;
import com.iohao.little.game.action.skeleton.core.flow.interal.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 骨架构建器<BR>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@Accessors(chain = true)
@Setter
public final class BarSkeletonBuilder {
    /** BarSkeletonSetting */
    @Getter
    final BarSkeletonSetting setting = new BarSkeletonSetting();

    /** handler 列表 */
    final List<Handler> handlers = new LinkedList<>();
    /** inout 列表 */
    final List<ActionMethodInOut> inOuts = new LinkedList<>();
    /** action class */
    final List<Class<?>> actionControllerClazzList = new LinkedList<>();
    /** action send class */
    final List<Class<?>> actionSendClazzList = new LinkedList<>();
    /** 错误码 */
    final List<MsgExceptionInfo> msgExceptionInfoList = new ArrayList<>();

    /** 命令执行器 */
    ActionCommandFlowExecute actionCommandFlowExecute = new DefaultActionCommandFlowExecute();
    /** action工厂 */
    ActionControllerFactoryBean<Object> actionControllerFactoryBean = new DefaultActionControllerFactoryBean<>();
    /** 框架执行完后, 最后需要做的事. 一般用于write数据到掉用端端 */
    ActionAfter actionAfter = new DefaultActionAfter();
    /** 结果包装器 */
    ActionMethodResultWrap actionMethodResultWrap = new DefaultActionMethodResultWrap();
    /** 异常处理 */
    ActionMethodExceptionProcess actionMethodExceptionProcess = new DefaultActionMethodExceptionProcess();
    /** InvokeActionMethod */
    ActionMethodInvoke actionMethodInvoke = new DefaultActionMethodInvoke();
    /** ActionMethod 方法参数解析器 */
    ActionMethodParamParser actionMethodParamParser = new DefaultActionMethodParamParser();

    /** 响应对象的创建 */
    ResponseMessageCreate responseMessageCreate = new DefaultResponseMessageCreate();
    /** 推送相关的文档 */
    ActionSendDocs actionSendDocs = new ActionSendDocs();
    /** 错误码相关的文档 */
    ErrorCodeDocs errorCodeDocs = new ErrorCodeDocs();

    BarSkeletonBuilder() {
    }

    private void before() {
        // 如果是 java 协议
        if (setting.parseType == ParseType.JAVA) {
            this.actionMethodParamParser = new ObjectActionMethodParamParser();
            this.actionMethodResultWrap = new ObjectActionMethodResultWrap();
        }
    }

    /**
     * 构建骨架, 提供了一些默认配置
     */
    public BarSkeleton build() {

        this.before();

        // 参数设置
        var barSkeleton = new BarSkeleton()
                .setOpenIn(setting.openIn)
                .setOpenOut(setting.openOut)
                // action command 命令执行器流程
                .setActionCommandFlowExecute(this.actionCommandFlowExecute)
                // action 工厂
                .setActionControllerFactoryBean(this.actionControllerFactoryBean)
                // ActionMethod Invoke 方法回掉
                .setActionMethodInvoke(this.actionMethodInvoke)
                // ActionMethod 方法参数解析器
                .setActionMethodParamParser(this.actionMethodParamParser)
                // ActionMethod 的异常处理
                .setActionMethodExceptionProcess(this.actionMethodExceptionProcess)
                // ActionMethod 结果包装器
                .setActionMethodResultWrap(this.actionMethodResultWrap)
                // action after 对action最后的处理; 一般用于把结果 write 到掉用端
                .setActionAfter(this.actionAfter)
                // 响应对象的创建
                .setResponseMessageCreate(this.responseMessageCreate)
                // 推送相关的文档
                .setActionSendDocs(this.actionSendDocs)
                // 错误码相关的文档
                .setErrorCodeDocs(this.errorCodeDocs);

        // 构建推送相关的文档信息
        this.actionSendDocs.buildActionSendDoc(this.actionSendClazzList);

        // handler
        extractedHandler(barSkeleton);

        // inout
        barSkeleton.inOutList.addAll(this.inOuts);

        // 构建 actionMapping
        extractedActionCommand(barSkeleton);

        log(barSkeleton);

        BarSkeletonDoc.me().addSkeleton(barSkeleton);

        return barSkeleton;
    }

    public BarSkeletonBuilder addMsgExceptionInfo(MsgExceptionInfo msgExceptionInfo) {
        Objects.requireNonNull(msgExceptionInfo);
        this.errorCodeDocs.addMsgExceptionInfo(msgExceptionInfo);
        return this;
    }

    public BarSkeletonBuilder addActionController(Class<?> controller) {
        Objects.requireNonNull(controller);
        this.actionControllerClazzList.add(controller);
        return this;
    }

    public BarSkeletonBuilder addActionSend(Class<?> actionSend) {
        Objects.requireNonNull(actionSend);
        this.actionSendClazzList.add(actionSend);
        return this;
    }

    public BarSkeletonBuilder addHandler(Handler handler) {
        Objects.requireNonNull(handler);
        // 先进先执行
        this.handlers.add(handler);
        return this;
    }

    /**
     * 添加 inOut
     *
     * @param inOut inOut
     * @return this
     */
    public BarSkeletonBuilder addInOut(ActionMethodInOut inOut) {
        Objects.requireNonNull(inOut);
        this.inOuts.add(inOut);
        return this;
    }

    private void extractedActionCommand(BarSkeleton barSkeleton) {
        // 命令信息构建器
        var actionCommandInfoBuilder = new ActionCommandInfoBuilder(setting)
                .buildAction(this.actionControllerClazzList);

        var map = actionCommandInfoBuilder.getMap();

        var actionCommandManager = barSkeleton.actionCommandManager;
        // map 转数组
        actionCommandManager.actionCommands = BarInternalKit.convertArray(map, setting);

        if (setting.isKeepActionMap()) {
            actionCommandManager.actionCommandMap.putAll(map);
        }

        actionCommandInfoBuilder.clean();
    }


    private void extractedHandler(BarSkeleton barSkeleton) {
        // 如果没有配置handler, 那么使用默认的
        if (this.handlers.isEmpty()) {
            barSkeleton.handlerList.add(new ActionCommandHandler());
        } else {
            barSkeleton.handlerList.addAll(this.handlers);
        }

        if (barSkeleton.handlerList.size() == 1) {
            barSkeleton.handler = barSkeleton.handlerList.get(0);
        }
    }

    private void log(BarSkeleton barSkeleton) {
        if (setting.isPrintHandler()) {
            PrintActionKit.printHandler(barSkeleton.getHandlerList());
        }

        if (setting.isPrintInout()) {
            PrintActionKit.printInout(barSkeleton.getInOutList());
        }

        if (setting.isPrintAction()) {
            PrintActionKit.printActionCommand(barSkeleton.actionCommandManager.actionCommands, setting.printActionShort);
        }
    }
}
