package com.jhc.csbot.script_interpreter.core.parser.handler.keyword;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 关键字处理工厂
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
public class KeywordHandlerFactory {
    private static final Map<String, AbstractIdentifierHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(String lexical, AbstractIdentifierHandler strategy) {
        STRATEGY_MAP.put(lexical, strategy);
    }

    public static AbstractIdentifierHandler getStrategy(String lexical) {
        AbstractIdentifierHandler strategy = STRATEGY_MAP.get(lexical);
        if (strategy == null) {
            throw new ScriptException(ScriptErrorEnum.IDENTIFIER_NOT_FOUND);
        }
        return strategy;
    }
}
