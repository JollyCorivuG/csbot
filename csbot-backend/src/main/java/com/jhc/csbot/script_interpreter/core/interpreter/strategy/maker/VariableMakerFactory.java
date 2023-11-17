package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 变量构造器工厂
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
public class VariableMakerFactory {
    private static final Map<String, AbstractVariableMaker> STRATEGY_MAP = new HashMap<>();

    public static void register(String variableType, AbstractVariableMaker strategy) {
        STRATEGY_MAP.put(variableType, strategy);
    }

    public static AbstractVariableMaker getStrategy(String variableType) {
        return STRATEGY_MAP.get(variableType);
    }
}
