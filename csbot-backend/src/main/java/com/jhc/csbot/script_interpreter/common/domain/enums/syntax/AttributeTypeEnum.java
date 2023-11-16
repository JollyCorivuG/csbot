package com.jhc.csbot.script_interpreter.common.domain.enums.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 属性类型枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Getter
@AllArgsConstructor
public enum AttributeTypeEnum {
    STRING(0, "单个字符串"),
    INT(1, "整数"),
    BOOLEAN(2, "布尔值"),
    OBJECT(3, "单个对象"),
    VARIABLE(4, "变量"),
    STRING_ARRAY(5, "字符串数组"),
    OBJECT_ARRAY(6, "对象数组"),
    ;


    private final Integer type;
    private final String desc;
}
