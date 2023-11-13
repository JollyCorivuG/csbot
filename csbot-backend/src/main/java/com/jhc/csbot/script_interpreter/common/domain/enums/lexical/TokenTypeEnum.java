package com.jhc.csbot.script_interpreter.common.domain.enums.lexical;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 词法单元类型枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Getter
@AllArgsConstructor
public enum TokenTypeEnum {
    KEYWORD(0, "关键词"),
    IDENTIFIER(1, "标识符"),
    STRING(2, "字符串"),
    NUMBER(3, "数字"),
    BOOLEAN(4, "布尔值"),
    DELIMITER(5, "分割符"),
    OPERATOR(6, "运算符"),
    ;

    private final Integer type;
    private final String desc;
}
