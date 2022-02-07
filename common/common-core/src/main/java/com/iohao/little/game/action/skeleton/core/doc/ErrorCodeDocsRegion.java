package com.iohao.little.game.action.skeleton.core.doc;

import java.util.*;

/**
 * 错误码域
 *
 * @author 洛朱
 * @date 2022-02-03
 */
public class ErrorCodeDocsRegion {

    Set<ErrorCodeDoc> errorCodeDocSet = new HashSet<>();

    public void addErrorCodeDocs(ErrorCodeDocs errorCodeDocs) {
        List<ErrorCodeDoc> errorCodeDocList = errorCodeDocs.getErrorCodeDocList();
        errorCodeDocSet.addAll(errorCodeDocList);
    }

    public List<ErrorCodeDoc> listErrorCodeDoc() {

        List<ErrorCodeDoc> list = new ArrayList<>(this.errorCodeDocSet);

        // 排序
        list.sort((o1, o2) -> o1.code - o2.getCode());

        return list;
    }
}
