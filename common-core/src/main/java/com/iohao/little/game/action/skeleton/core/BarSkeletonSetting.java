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
    /** 默认:true; 默认tcp action对象是single. 如果设置为false, 每次创建新的tcp action对象. */
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
}
