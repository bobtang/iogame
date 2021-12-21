package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.*;
import com.iohao.little.game.action.skeleton.core.flow.interal.*;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * 骨架构建器<BR>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@Accessors(chain = true)
@Setter
public final class BarSkeletonBuilder {
    /** 默认tcp对象是single. 如果设置为false, 每次创建新的tcp对象. 默认:true */
    boolean createSingleActionCommandController = true;
    boolean openIn = true;
    boolean openOut = true;
    /** handler 列表 */
    final List<Handler<RequestMessage>> handlers = new LinkedList<>();
    /** inout 列表 */
    final List<ActionMethodInOut> inOuts = new LinkedList<>();
    /** action class */
    final List<Class<?>> controllerClazzList = new LinkedList<>();
    /** BarSkeletonSetting */
    @Getter
    final BarSkeletonSetting barSkeletonSetting = new BarSkeletonSetting();
    /** 命令执行器 */
    ActionCommandFlowExecute actionCommandFlowExecute = new DefaultActionCommandFlowExecute();
    /** action工厂 */
    ActionControllerFactoryBean<Object> actionControllerFactoryBean = new DefaultActionControllerFactoryBean<>();
    /** 框架执行完后, 最后需要做的事. 一般用于write数据到掉用端端 */
    ActionAfter<RequestMessage, ResponseMessage> actionAfter = new DefaultActionAfter();
    /** 结果包装器 */
    ActionMethodResultWrap<RequestMessage, ResponseMessage> actionMethodResultWrap = new DefaultActionMethodResultWrap();
    /** 异常处理 */
    ActionMethodExceptionProcess actionMethodExceptionProcess = new DefaultActionMethodExceptionProcess();
    /** InvokeActionMethod */
    ActionMethodInvoke actionMethodInvoke = new DefaultActionMethodInvoke();
    /** ActionMethod 方法参数解析器 */
    ActionMethodParamParser actionMethodParamParser = new DefaultActionMethodParamParser();

    BarSkeletonBuilder() {
    }

    /**
     * 构建骨架, 提供了一些默认配置
     */
    public BarSkeleton build() {

        // 参数设置
        var barSkeleton = new BarSkeleton()
                .setOpenIn(this.openIn)
                .setOpenOut(this.openOut)
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
                .setActionAfter(this.actionAfter);

        // handler
        extractedHandler(barSkeleton);

        // inout
        extractedInout(barSkeleton);

        // 构建 actionMapping
        extractedActionCommand(barSkeleton);

        log(barSkeleton);

        return barSkeleton;
    }

    private void extractedActionCommand(BarSkeleton barSkeleton) {
        // 命令信息构建器
        var actionCommandInfoBuilder = new ActionCommandInfoBuilder(barSkeletonSetting)
                .buildAction(this.controllerClazzList);

        var map = actionCommandInfoBuilder.getMap();

        var actionCommandManager = barSkeleton.actionCommandManager;
        // map 转数组
        actionCommandManager.actionCommands = BarInternalKit.convertArray(map, barSkeletonSetting);

        if (barSkeletonSetting.isKeepActionMap()) {
            actionCommandManager.actionCommandMap.putAll(map);
        }

        map.clear();
    }

    private void extractedInout(BarSkeleton barSkeleton) {
        if (this.inOuts.isEmpty()) {
            barSkeleton.inOuts.add(new DebugInOut());
        } else {
            barSkeleton.inOuts.addAll(this.inOuts);
        }
    }

    private void extractedHandler(BarSkeleton barSkeleton) {
        // 如果没有配置handler, 那么使用默认的
        if (this.handlers.isEmpty()) {
            barSkeleton.handlers.add(new ActionCommandHandler());
        } else {
            barSkeleton.handlers.addAll(this.handlers);
        }

        if (barSkeleton.handlers.size() == 1) {
            barSkeleton.singleHandler = true;
            barSkeleton.handler = barSkeleton.handlers.get(0);
        }
    }

    private void log(BarSkeleton barSkeleton) {
        if (barSkeletonSetting.isPrintHandler()) {
            PrintActionKit.printHandler(barSkeleton.getHandlers());
        }

        if (barSkeletonSetting.isPrintInout()) {
            PrintActionKit.printInout(barSkeleton.getInOuts());
        }

        if (barSkeletonSetting.isPrintAction()) {
            PrintActionKit.printActionCommand(barSkeleton.actionCommandManager.actionCommands, barSkeletonSetting.printActionShort);
        }
    }

    private void n(Object o) {
        if (Objects.isNull(o)) {
            throw new NullPointerException("must not null 小sb !");
        }
    }

    public BarSkeletonBuilder addActionController(Class<?> controller) {
        n(controller);
        this.controllerClazzList.add(controller);
        return this;
    }

    public BarSkeletonBuilder addHandler(Handler handler) {
        n(handler);
        // 先进先执行
        this.handlers.add(handler);
        return this;
    }

    public BarSkeletonBuilder addInOut(ActionMethodInOut inOut) {
        n(inOut);
        this.inOuts.add(inOut);
        return this;
    }


}
