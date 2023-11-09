package com.jhc.csbot.script_interpreter.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 语法树节点标签枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Getter
@AllArgsConstructor
public enum SyntaxTreeLabelEnum {
    START_FLAG(0, "开始标识"),
    VARIABLE_NAME(1, "变量名"),
    IDENTIFIER(2, "标识符"),
    ATTRIBUTE(3, "属性"),
    ;

    private final Integer type;
    private final String desc;
}
