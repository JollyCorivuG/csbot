package com.jhc.csbot.script_interpreter.common.domain.enums.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 脚本错误枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Getter
@AllArgsConstructor
public enum ScriptErrorEnum {
    VARIABLE_NOT_FOUND(10000, "变量未定义"),
    VARIABLE_TYPE_ERROR(10001, "变量类型错误"),
    VARIABLE_NAME_ERROR(10002, "变量名错误"),
    VARIABLE_REPEATED_DEFINITION(10003, "变量重复定义"),
    SYNTAX_ERROR(20000, "语法错误"),
    ATTRIBUTE_MISSING(30000, "缺少相关属性"),
    ATTRIBUTE_REPEATED_DEFINITION(30001, "属性重复定义"),
    ATTRIBUTE_NOT_FOUND(30002, "未知的属性"),
    ATTRIBUTE_TYPE_ERROR(30003, "属性类型错误"),
    ATTRIBUTE_TYPE_NOT_FOUND(30004, "未知的属性类型"),
    FILE_NOT_FOUND(40000, "脚本文件未找到"),
    FILE_READ_ERROR(40001, "脚本文件读取错误"),
    FILE_CLOSE_ERROR(40002, "脚本文件关闭错误"),
    SYNTAX_TREE_ERROR(50000, "语法树错误"),
    IDENTIFIER_NOT_FOUND(60000, "未知的标识符"),
    ;

    private final Integer code;
    private final String message;
}
