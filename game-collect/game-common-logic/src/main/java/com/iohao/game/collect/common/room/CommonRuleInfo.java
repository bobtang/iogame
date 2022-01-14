package com.iohao.game.collect.common.room;

import com.iohao.game.collect.common.function.ToJson;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

/**
 * 玩法规则信息
 *
 * @author 洛朱
 * @date 2022-01-14
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonRuleInfo implements RuleInfo, Serializable, ToJson {
    @Serial
    private static final long serialVersionUID = -5169477928235457487L;
}
