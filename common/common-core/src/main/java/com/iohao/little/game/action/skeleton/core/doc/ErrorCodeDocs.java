package com.iohao.little.game.action.skeleton.core.doc;

import com.iohao.little.game.action.skeleton.core.exception.MsgExceptionInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误码文档相关
 *
 * @author 洛朱
 * @date 2022-02-03
 */
@Slf4j
public class ErrorCodeDocs {
    @Getter
    List<ErrorCodeDoc> errorCodeDocList = new ArrayList<>();

    public void addMsgExceptionInfo(MsgExceptionInfo msgExceptionInfo) {
        ErrorCodeDoc errorCodeDoc = new ErrorCodeDoc()
                .setCode(msgExceptionInfo.getCode())
                .setMsg(msgExceptionInfo.getMsg());

        this.errorCodeDocList.add(errorCodeDoc);
    }

}
