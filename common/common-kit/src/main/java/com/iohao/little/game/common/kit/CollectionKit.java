package com.iohao.little.game.common.kit;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合相关工具
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class CollectionKit {
    /**
     * 分组统计
     * <pre>
     *     key is 元素下标
     *     value is 元素下标对应的数量
     * </pre>
     *
     * <pre>
     *     示例
     *     handCards: [11, 11, 11, 21, 46, 33,33, 18, 18, 18, 18]
     *
     *     得到的 map {
     *         11 : 3
     *         18 : 4
     *         21 : 1
     *         33 : 2
     *         46 : 1
     *     }
     * </pre>
     *
     * @param list 元素列表
     * @return map
     */
    public static Map<Integer, Integer> groupCounting(List<Integer> list) {
        return list.stream().
                collect(
                        Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1))
                );
    }

    /**
     * 将对象转换成 map
     *
     * @param dto 对象
     * @return map
     */
    static public Map<String, Object> toMap(Object dto) {

        String jsonStr = JSONObject.toJSONString(dto);
        JSONObject json = JSONObject.parseObject(jsonStr);
        Map<String, Object> paras = new HashMap<>();

        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();

            Object value = entry.getValue();

            if (Objects.nonNull(value)) {
                paras.put(key, value.toString());
            }
        }

        return paras;
    }
}
