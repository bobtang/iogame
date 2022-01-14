package com.iohao.game.collect.common.function;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 洛朱
 * @date 2022-01-14
 */
public interface ToJson {
    /**
     * 将对象转换成 json 格式
     *
     * @return json String
     */
    default String toJson() {
        return JSONObject.toJSONString(this);
    }
}
