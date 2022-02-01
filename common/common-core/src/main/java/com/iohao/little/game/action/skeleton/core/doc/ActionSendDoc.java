package com.iohao.little.game.action.skeleton.core.doc;

import com.iohao.little.game.action.skeleton.annotation.DocActionSend;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author 洛朱
 * @date 2022-02-01
 */
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionSendDoc {
    /** 主路由 */
    final int cmd;
    /** 子路由 */
    final int subCmd;
    /** 业务类型 */
    final Class<?> dataClass;
    /** 推送描述 */
    final String description;
    /** true 已经被读取过一次或以上 */
    boolean read;

    public ActionSendDoc(DocActionSend docActionSend) {
        this.cmd = docActionSend.cmd();
        this.subCmd = docActionSend.subCmd();
        this.dataClass = docActionSend.dataClass();
        this.description = docActionSend.description();
    }
}
