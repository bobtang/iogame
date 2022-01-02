package com.iohao.little.game.common.kit.weight;

import cn.hutool.core.util.RandomUtil;

import java.util.List;
import java.util.Objects;

/**
 * 权重工具
 *
 * @author 洛朱
 * @date 2022-01-02
 * @see WeightKit
 */
public class WeightKit {
    /**
     * 随机抽取 根据权重
     * <pre>
     *      将列表的权重值相加，随机获取该权重值
     *      在遍历列表，并累积元素的权重值。只要当前元素权重大于这个随机值，就返回元素
     *      如果没有找到大于随机权重值，那么就返回权重最大的元素
     * </pre>
     *
     * @param weights 权重值列表，如果list不为null or empty,那么返回值一定不为null
     * @param <T>     T
     * @return 权重值大的元素的获取几率会比较大
     */
    @SuppressWarnings("unchecked")
    public static <T extends Weight> T roll(List<? extends Weight> weights) {
        if (Objects.isNull(weights) || weights.isEmpty()) {
            return null;
        }

        int weightTotal = 0;
        int maxWeightValue = 0;
        Weight maxWeight = null;

        for (Weight weight : weights) {
            // 获取最大权重值的元素
            if (weight.getWeightVal() > maxWeightValue) {
                maxWeightValue = weight.getWeightVal();
                maxWeight = weight;
            }
            // 权重值累积
            weightTotal += weight.getWeightVal();
        }

        // 随机获取该权重值
        int random = RandomUtil.randomInt(weightTotal + 1);
        weightTotal = 0;
        for (Weight weight : weights) {
            // 遍历列表，并累积元素的权重值
            weightTotal += weight.getWeightVal();
            // 只要当前元素权重大于这个随机值，就返回元素
            if (weightTotal > random) {
                return (T) weight;
            }
        }

        // 如果没有找到大于随机权重值，那么就返回权重最大的元素
        return (T) maxWeight;
    }
}
