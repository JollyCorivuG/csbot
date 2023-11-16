package com.jhc.csbot.script_interpreter.common.domain.enums.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 词法错误枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Getter
@AllArgsConstructor
public enum LexicalErrorEnum {
    UNKNOWN_SYMBOL(10000, "未知的符号"),
    INVALID_IDENTIFIER_NAME(20000, "不合法的标识符名称"),
    UNCLOSED_STRING(30000, "未闭合的字符串"),
    ;

    private final Integer code;
    private final String message;
}
