package com.iohao.little.game.action.skeleton.core.doc;

import com.iohao.little.game.action.skeleton.annotation.DocActionBroadcast;
import com.iohao.little.game.action.skeleton.core.ActionCommand;

import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-28
 */
public class DocActionBroadcastInfo {

    String methodParam;
    DocActionBroadcast docActionBroadcast;

    public DocActionBroadcastInfo(ActionCommand actionCommand) {
        this.init(actionCommand);
    }

    void init(ActionCommand actionCommand) {
        DocActionBroadcast docActionBroadcast = actionCommand.getActionMethod().getAnnotation(DocActionBroadcast.class);
        this.docActionBroadcast = docActionBroadcast;

        if (Objects.isNull(docActionBroadcast)) {
            this.methodParam = "";
            return;
        }

        Class<?> broadcastClass = docActionBroadcast.value();
        if (!Object.class.equals(broadcastClass)) {
            this.methodParam = broadcastClass.getName();
        }
    }

    public void setMethodParam(String methodParam) {
        if (Objects.isNull(this.methodParam)) {
            this.methodParam = methodParam;
        }
    }


}
