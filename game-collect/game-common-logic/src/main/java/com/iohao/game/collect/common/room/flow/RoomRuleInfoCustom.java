package com.iohao.game.collect.common.room.flow;

import com.iohao.game.collect.common.room.RuleInfo;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * 游戏规则信息解析
 * <pre>
 *     具体的玩法规则协议, 在 json 中完成
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public interface RoomRuleInfoCustom {
    /**
     * 获取子游戏规则信息
     *
     * @param ruleInfoJson 游戏规则 - json
     * @return 规则信息
     * @throws MsgException e
     */
    RuleInfo getRuleInfo(String ruleInfoJson) throws MsgException;
}
