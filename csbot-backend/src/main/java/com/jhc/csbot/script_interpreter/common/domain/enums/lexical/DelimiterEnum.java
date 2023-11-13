package com.jhc.csbot.script_interpreter.common.domain.enums.lexical;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 分割符枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Getter
@AllArgsConstructor
public enum DelimiterEnum {
    LEFT_BRACE("{", "左大括号"),
    RIGHT_BRACE("}", "右大括号"),
    LEFT_MIDDLE_BRACKET("[", "左中括号"),
    RIGHT_MIDDLE_BRACKET("]", "右中括号"),
    END_OF_STATEMENT(";", "语句结束符"),
    COMMA(",", "逗号"),
    ;

    private final String delimiter;
    private final String desc;

    private static final Map<String, DelimiterEnum> cache;

    static {
        cache = Arrays.stream(DelimiterEnum.values()).collect(Collectors.toMap(DelimiterEnum::getDelimiter, Function.identity()));
    }

    public static DelimiterEnum of(String delimiter) {
        return cache.get(delimiter);
    }
}
