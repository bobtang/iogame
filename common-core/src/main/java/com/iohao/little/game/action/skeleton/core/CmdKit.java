package com.iohao.little.game.action.skeleton.core;

import cn.hutool.core.util.StrUtil;

public class CmdKit {
    /**
     * 从 merge 中获取 [高16位] 的数值
     *
     * @param merge 结果
     * @return [高16位] 的数值
     */
    public static int getCmd(int merge) {
        return merge >> 16;
    }

    /**
     * 从 merge 中获取 [低16位] 的数值
     *
     * @param merge 结果
     * @return [低16位] 的数值
     */
    public static int getSubCmd(int merge) {
        return merge & 0xFFFF;
    }

    /**
     * 合并两个参数,分别存放在 [高16 和 低16]
     * <pre>
     *     cmd - 高16
     *     subCmd - 低16
     *     例如 cmd = 600; subCmd = 700;
     *     merge 的结果: 39322300
     *     那么merge对应的二进制是: 0000 0010 0101 1000 0000 0010 1011 1100
     * </pre>
     *
     * @param cmd    主 cmd 存放于合并结果的高16位, 该参数不得大于 32767
     * @param subCmd 子 存放于合并结果的低16位, 该参数不得大于 65535
     * @return 合并的结果
     */
    public static int merge(int cmd, int subCmd) {
        return (cmd << 16) + subCmd;
    }

    public static String mergeToString(int merge) {
        int cmd = getCmd(merge);
        int subCmd = getSubCmd(merge);
        String template = "[{}-{}-{}]";
        return StrUtil.format(template, cmd, subCmd, merge);
    }
}
