package com.jhc.csbot.script_interpreter.common.domain.enums.lexical;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 运算符枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Getter
@AllArgsConstructor
public enum OperatorEnum {
    MEMBER_ACCESS(".", "成员访问"),
    ;

    private final String operator;
    private final String desc;

    private static final Map<String, OperatorEnum> cache;

    static {
        cache = Arrays.stream(OperatorEnum.values()).collect(Collectors.toMap(OperatorEnum::getOperator, Function.identity()));
    }

    public static OperatorEnum of(String operator) {
        return cache.get(operator);
    }
}
