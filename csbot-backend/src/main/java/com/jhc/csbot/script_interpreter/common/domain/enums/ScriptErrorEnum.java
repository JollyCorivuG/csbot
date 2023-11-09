package com.jhc.csbot.script_interpreter.common.domain.enums;

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
    SYNTAX_ERROR(20000, "语法错误"),
    ATTRIBUTE_MISSING(30000, "缺少相关属性"),
    FILE_NOT_FOUND(40000, "脚本文件未找到"),
    FILE_READ_ERROR(40001, "脚本文件读取错误"),
    FILE_CLOSE_ERROR(40002, "脚本文件关闭错误"),

    ;

    private final Integer code;
    private final String message;
}
