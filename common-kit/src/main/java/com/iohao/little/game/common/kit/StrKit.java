package com.iohao.little.game.common.kit;

import lombok.experimental.UtilityClass;

/**
 * @author 洛朱
 * @date 2022-01-02
 */
@UtilityClass
public class StrKit {
    private final char A_LOWER = 'a';
    private final char A_UPPER = 'A';
    private final char Z_LOWER = 'z';
    private final char Z_UPPER = 'Z';

    private int x = A_LOWER - A_UPPER;

    /**
     * 首字母转换为大写
     *
     * @param str 字符串
     * @return 转换后的字符串
     */
    public String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= A_LOWER && firstChar <= Z_LOWER) {
            char[] arr = str.toCharArray();
            arr[0] -= x;
            return String.valueOf(arr);
        }
        return str;
    }

    /**
     * 首字母转换为小写
     *
     * @param str 字符串
     * @return 转换后的字符串
     */
    public String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= A_UPPER && firstChar <= Z_UPPER) {
            char[] arr = str.toCharArray();
            arr[0] += x;
            return String.valueOf(arr);
        }
        return str;
    }
}
