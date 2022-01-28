package com.iohao.little.game.action.skeleton.core.doc;

import lombok.Data;

/**
 * action command 文档
 * <pre>
 *     存放源代码信息
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-22
 */
@Data
public class ActionCommandDoc {
    int classLineNumber = 1;
    String classComment = "";

    /** 代码所在行 */
    int lineNumber = 1;

    String comment = "";
}
