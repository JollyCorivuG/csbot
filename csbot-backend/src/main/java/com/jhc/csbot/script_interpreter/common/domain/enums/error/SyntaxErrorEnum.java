package com.jhc.csbot.script_interpreter.common.domain.enums.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 语法错误枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Getter
@AllArgsConstructor
public enum SyntaxErrorEnum {
    UNEXPECTED_TOKEN(10000, "非预期的符号"),
    NO_COMPLETE_STATEMENT(20000, "不完整的语句"),
    VARIABLE_NOT_FOUND(30000, "变量未定义"),
    VARIABLE_TYPE_ERROR(30001, "变量类型错误"),
    VARIABLE_REPEATED_DEFINITION(30002, "变量重复定义"),
    ATTRIBUTE_MISSING(40000, "缺少相关属性"),
    ATTRIBUTE_REPEATED_DEFINITION(40001, "属性重复定义"),
    ATTRIBUTE_NOT_FOUND(40002, "未知的属性"),
    ATTRIBUTE_TYPE_ERROR(40003, "属性类型错误"),
    ATTRIBUTE_TYPE_NOT_FOUND(40004, "未知的属性类型"),
    ARRAY_NOT_CORRECT_ELEMENT_DELIMITER(50000, "不是正确的数组元素分割符"),
    ARRAY_ELEMENT_TYPE_INCONSISTENCY(50001, "数组元素类型不一致"),
    ARRAY_EMPTY(50002, "数组为空"),
    ARRAY_TYPE_NOT_SUPPORTED(50003, "不支持的数组类型"),
    OBJECT_EMPTY(60000, "对象为空"),
    NOT_SUPPORT_USE(70000, "不支持的用法"),
    ;
    private final Integer code;
    private final String message;
}
