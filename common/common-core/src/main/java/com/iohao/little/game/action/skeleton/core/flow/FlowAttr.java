package com.iohao.little.game.action.skeleton.core.flow;

import com.iohao.little.game.common.kit.attr.AttrOption;

/**
 * flow 上下文的一些扩展属性
 *
 * @author 洛朱
 * @date 2022-01-31
 */
public interface FlowAttr {
    /** 路由 */
    AttrOption<Integer> cmdMerge = AttrOption.valueOf("cmdMerge");
    /** 异常消息 */
    AttrOption<String> msgException = AttrOption.valueOf("msgException");
}
