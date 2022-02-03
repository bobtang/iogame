package com.iohao.little.game.action.skeleton.core.doc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 洛朱
 * @date 2022-02-03
 */
@Data
@Accessors(chain = true)
public class ErrorCodeDoc {
    /** 异常码 */
    int code;
    /** 异常消息 */
    String msg;
}
