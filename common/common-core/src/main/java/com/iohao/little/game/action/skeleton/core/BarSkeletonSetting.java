package com.iohao.little.game.action.skeleton.core;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务框架 Setting
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Getter
@Setter
public class BarSkeletonSetting {
    /**
     * <pre>
     *     true: action 对象是 single.
     *     false: 每次都创建新的 action 对象.
     * </pre>
     */
    boolean createSingleActionCommandController = true;

    /** action 的默认长度 (一级 cmd) */
    int actionMaxLen = 127;
    /** 子 action 的默认长度 (二级 subCmd) */
    int subActionMaxLen = 127;

    /** 是否保留 action Map */
    boolean keepActionMap = true;

    /** true action 日志打印 */
    boolean printAction = true;
    /** false action 日志打印短名称(类、参数名、返回值) */
    boolean printActionShort = true;
    /** true inout 日志打印 */
    boolean printInout = true;
    /** true handler 日志打印 */
    boolean printHandler = true;

    /** inOut 的 in 。 true 开启 */
    boolean openIn = true;
    /** inOut 的 out 。 true 开启 */
    boolean openOut = true;
    /** 解析类型 */
    ParseType parseType = ParseType.JAVA;
}
