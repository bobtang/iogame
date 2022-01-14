package com.iohao.little.game.common.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组相关工具
 *
 * @author 洛朱
 * @date 2022-01-14
 */
public class ArrayKit {

    /**
     * 统计数组数量
     *
     * @return 数量
     */
    public static int sum(int[] cards) {
        return Arrays.stream(cards).sum();
    }

    public static int[] copy(int[] cards) {
        int length = cards.length;
        int[] copyCards = new int[length];
        System.arraycopy(cards, 0, copyCards, 0, length);
        return copyCards;
    }

    public static List<Integer> toList(int[] cards) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            int num = cards[i];
            if (num == 0) {
                continue;
            }

            for (int j = 0; j < num; j++) {
                list.add(i);
            }
        }

        return list;
    }

    public static void subtract(int[] cards, int[] beCards) {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = cards[i] - beCards[i];
        }
    }

    public static void plus(int[] cards, int[] beCards) {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = cards[i] + beCards[i];
        }
    }

    public static void plus(int[] cards, List<Integer> beCards) {
        for (Integer value : beCards) {
            cards[value]++;
        }
    }

    public static List<Integer> random(int[] cards, int size) {
        List<Integer> list = toList(cards);
        list = list.subList(0, size);
        return list;
    }
}
