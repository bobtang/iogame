package com.iohao.game.collect.common.room.operation;

import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * 玩法操作业务类, 将验证与操作分离
 * <pre>
 *     坦克:
 *     射子弹、发射导弹 等操作
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface OperationHandler {
    /**
     * 检测验证, 验证用户操作步骤是否合法
     *
     * @param context 操作上下文
     * @throws MsgException e
     */
    void verify(OperationContext context) throws MsgException;

    /**
     * 验证通过后, 执行处理
     *
     * @param context 操作上下文
     */
    void process(OperationContext context);
}
