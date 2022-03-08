package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * InOut 插件相关
 *
 * @author 洛朱
 * @date 2022-03-08
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InOutInfo {
    /** true : 开放拦截 in */
    final boolean openIn;
    /** true : 开放拦截 out */
    final boolean openOut;

    /** true 只有一个 inout 插件 */
    final boolean single;
    ActionMethodInOut singleInOut;

    /** inoutList */
    final List<ActionMethodInOut> inOutList = new ArrayList<>();


    InOutInfo(BarSkeletonSetting setting, List<ActionMethodInOut> inOuts) {
        this(setting.openIn, setting.openOut, inOuts);
    }

    private InOutInfo(boolean openIn, boolean openOut, List<ActionMethodInOut> inOuts) {
        this.inOutList.addAll(inOuts);

        if (this.inOutList.isEmpty()) {
            openIn = false;
            openOut = false;
        }

        this.openIn = openIn;
        this.openOut = openOut;
        this.single = this.inOutList.size() == 1;

        if (this.single) {
            singleInOut = this.inOutList.get(0);
        }
    }

    public void fuckIn(FlowContext flowContext) {
        if (!this.openIn) {
            return;
        }

        if (this.single) {
            singleInOut.fuckIn(flowContext);
            return;
        }

        for (ActionMethodInOut inOut : inOutList) {
            inOut.fuckIn(flowContext);
        }
    }

    public void fuckOut(FlowContext flowContext) {
        if (!this.openOut) {
            return;
        }

        if (this.single) {
            singleInOut.fuckOut(flowContext);
            return;
        }

        for (ActionMethodInOut inOut : inOutList) {
            inOut.fuckOut(flowContext);
        }
    }
}
