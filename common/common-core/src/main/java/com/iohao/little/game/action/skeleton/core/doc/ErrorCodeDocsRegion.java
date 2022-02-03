package com.iohao.little.game.action.skeleton.core.doc;

import cn.hutool.core.collection.CollectionUtil;

import java.util.*;

/**
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

        // 排序
        List<ErrorCodeDoc> list = new ArrayList<>(this.errorCodeDocSet);

        CollectionUtil.sort(list, new Comparator<ErrorCodeDoc>() {
            @Override
            public int compare(ErrorCodeDoc o1, ErrorCodeDoc o2) {
                return o1.code - o2.getCode();
            }
        });

        return list;
    }
}
