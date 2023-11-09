package com.jhc.csbot.script_interpreter.utils;

/**
 * @Description: 字符串工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
public class StringUtils {
    /**
     * 判断字符是否为空白字符
     *
     * @param c 字符
     * @return 是否为空白字符
     */
    public static boolean isBlankChar(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }
}
