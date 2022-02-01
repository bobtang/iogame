package com.iohao.little.game.action.skeleton.core.doc;

import com.iohao.little.game.action.skeleton.core.CmdKit;
import org.jctools.maps.NonBlockingHashMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-02-01
 */
public class ActionSendDocsRegion {
    Map<Integer, ActionSendDoc> actionSendDocMap = new NonBlockingHashMap<>();

    public void addActionSendDocs(ActionSendDocs actionSendDocs) {
        this.actionSendDocMap.putAll(actionSendDocs.getActionSendDocMap());
    }

    public ActionSendDoc getActionSendDoc(int cmd, int subCmd) {
        return this.getActionSendDoc(CmdKit.merge(cmd, subCmd));
    }

    public ActionSendDoc getActionSendDoc(int cmdMerge) {
        ActionSendDoc actionSendDoc = actionSendDocMap.get(cmdMerge);

        if (Objects.nonNull(actionSendDoc)) {
            actionSendDoc.setRead(true);
        }

        return actionSendDoc;
    }

    public List<ActionSendDoc> listActionSendDoc() {
        return this.actionSendDocMap.values()
                .stream()
                .filter(actionSendDoc -> !actionSendDoc.isRead())
                .toList();
    }

}
