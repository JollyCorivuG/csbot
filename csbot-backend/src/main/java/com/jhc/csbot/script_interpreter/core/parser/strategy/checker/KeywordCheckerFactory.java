package com.jhc.csbot.script_interpreter.core.parser.strategy.checker;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 关键词检查器工厂类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
public class KeywordCheckerFactory {
    private static final Map<String, AbstractKeywordChecker> STRATEGY_MAP = new HashMap<>();

    public static void register(String keyword, AbstractKeywordChecker strategy) {
        STRATEGY_MAP.put(keyword, strategy);
    }

    public static AbstractKeywordChecker getStrategy(String keyword) {
        AbstractKeywordChecker strategy = STRATEGY_MAP.get(keyword);
        if (strategy == null) {
            throw new ScriptException(ScriptErrorEnum.KEYWORD_NOT_FOUND);
        }
        return strategy;
    }
}
