package com.iohao.game.collect.tank.room.flow;

import com.iohao.game.collect.common.room.CommonRuleInfo;
import com.iohao.game.collect.common.room.RuleInfo;
import com.iohao.game.collect.common.room.flow.RoomRuleInfoCustom;
import com.iohao.little.game.action.skeleton.core.exception.MsgException;

/**
 * 坦克游戏规则
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class TankRoomRuleInfoCustom implements RoomRuleInfoCustom {
    static final CommonRuleInfo commonRuleInfo = new CommonRuleInfo();

    @Override
    public RuleInfo getRuleInfo(String ruleInfoJson) throws MsgException {
        // 暂时没什么特殊规则，不做 json 解析，使用默认的空规则
        return commonRuleInfo;
    }
}
