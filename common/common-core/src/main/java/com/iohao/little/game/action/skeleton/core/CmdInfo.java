package com.iohao.little.game.action.skeleton.core;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * cmdInfo 命令路由信息
 * <pre>
 *     平常大部分框架使用一个 cmd 来约定协议
 *     这里使用cmd,subCmd是为了模块的划分清晰, 当然这样规划还有更多好处. 但目前不是讨论的重点
 * </pre>
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
@Getter
public final class CmdInfo {
    /** 业务主路由 */
    final int cmd;
    /** 业务子路由 */
    final int subCmd;

    /**
     * 合并两个参数,分别存放在 [高16 和 低16]
     * <pre>
     *     cmd - 高16
     *     subCmd - 低16
     *     例如 cmd = 600; subCmd = 700;
     *     merge 的结果: 39322300
     *     那么 cmdMerge 对应的二进制是: [0000 0010 0101 1000] [0000 0010 1011 1100]
     * </pre>
     */
    final int cmdMerge;

    final String info;

    CmdInfo(int cmd, int subCmd) {
        // -------------- 路由相关 --------------
        this.cmd = cmd;
        this.subCmd = subCmd;
        this.cmdMerge = CmdKit.merge(cmd, subCmd);

        String template = "[cmd:{} - subCmd:{} - cmdMerge:{}]";
        this.info = StrUtil.format(template, cmd, subCmd, cmdMerge);

    }

    @Override
    public String toString() {
        return info;
    }
}
