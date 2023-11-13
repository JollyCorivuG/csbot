package com.jhc.csbot.script_interpreter.common.domain.enums.lexical;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 关键字枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Getter
@AllArgsConstructor
public enum KeywordEnum {
    ENV("env", "环境变量 env"),
    GLOBAL("global", "全局变量 global"),
    QUERY("query", "sql 查询变量 query"),
    INTENT("intent", "意图 intent"),
    ACTION("action", "动作 action"),
    STATUS("status", "状态 status"),
    ;

    private final String keyword;
    private final String desc;

    private static final Map<String, KeywordEnum> cache;

    static {
        cache = Arrays.stream(KeywordEnum.values()).collect(Collectors.toMap(KeywordEnum::getKeyword, Function.identity()));
    }

    public static KeywordEnum of(String keyword) {
        keyword = keyword.toLowerCase();
        return cache.get(keyword);
    }
}
