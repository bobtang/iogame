package com.iohao.little.game.action.skeleton.core;

import java.util.*;

public class ActionCommandManager {
    /**
     * action map
     * <pre>
     *     key 是类的 cmd
     *     value 是 map {
     *         key 是 方法的 subCmd
     *         value 是 actionCommand 信息
     *     }
     * </pre>
     */
    final Map<Integer, Map<Integer, ActionCommand>> actionCommandMap = new HashMap<>();

    /**
     * action 数组. 下标对应 cmd
     */
    ActionCommand[][] actionCommands;


    /**
     * 获取命令处理器
     *
     * @param cmd    路由
     * @param subCmd 子路由
     * @return 命令处理器
     */
    public ActionCommand getActionCommand(int cmd, int subCmd) {

        if (cmd >= actionCommands.length) {
            return null;
        }

        var subActionCommands = actionCommands[cmd];

        if (subCmd >= subActionCommands.length) {
            return null;
        }

        return actionCommands[cmd][subCmd];
    }

    public List<ActionCommand> listActionCommand() {
        List<ActionCommand> actionCommandList = new LinkedList<>();
        for (Map<Integer, ActionCommand> value : this.actionCommandMap.values()) {
            actionCommandList.addAll(value.values());
        }
        return actionCommandList;
    }

    public int[] arrayCmdMerge() {
        var actionCommands = this.listActionCommand();
        var cmdMergeArray = new int[actionCommands.size()];

        var i = 0;
        for (ActionCommand actionCommand : actionCommands) {
            cmdMergeArray[i++] = actionCommand.getCmdInfo().getCmdMerge();
        }

        return cmdMergeArray;
    }

}
