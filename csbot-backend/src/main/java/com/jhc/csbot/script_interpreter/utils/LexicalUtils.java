package com.jhc.csbot.script_interpreter.utils;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.LexicalErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.DelimiterEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.OperatorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;

/**
 * @Description: 词法分析工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
public class LexicalUtils {
    /**
     * 根据词法的值获取词法单元类型
     * @param value
     * @return
     */
    public static TokenTypeEnum getTokenType(String value, Integer line) {
        // 1.先判断是否是关键字
        if (KeywordEnum.of(value) != null) {
            return TokenTypeEnum.KEYWORD;
        }

        // 2.判断是否是数字
        try {
            Integer.parseInt(value);
            return TokenTypeEnum.NUMBER;
        } catch (NumberFormatException e) {
            // 不是数字, do nothing
        }

        // 3.判断是否是布尔值
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return TokenTypeEnum.BOOLEAN;
        }

        // 4.判断是否是分割符
        if (DelimiterEnum.of(value) != null) {
            return TokenTypeEnum.DELIMITER;
        }

        // 5.判断是否是运算符
        if (OperatorEnum.of(value) != null) {
            return TokenTypeEnum.OPERATOR;
        }

        // 6.说明是标识符
        // 6.1判断标识符的合法性, 标识符的规则: 可以含有中文、下划线、字母、数字，但是不能以数字开头
        if (value.matches("[a-zA-Z_一-龥][a-zA-Z0-9_一-龥]*")) {
            return TokenTypeEnum.IDENTIFIER;
        }
        // 6.2如果以数字开头，那么说明是不合法的标识符命名
        if (value.matches("[0-9][a-zA-Z0-9_一-龥]*")) {
            ErrorUtils.declareLexicalError(LexicalErrorEnum.INVALID_IDENTIFIER_NAME, line, value + " 不是合法的标识符命名");
            return null;
        }
        // 6.3说明含有非法字符
        ErrorUtils.declareLexicalError(LexicalErrorEnum.UNKNOWN_SYMBOL, line, value + " 中出现了非法字符");

        return null;
    }
}
