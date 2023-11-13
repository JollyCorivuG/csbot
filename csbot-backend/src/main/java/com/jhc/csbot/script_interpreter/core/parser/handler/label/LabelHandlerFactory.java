package com.jhc.csbot.script_interpreter.core.parser.handler.label;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 标签处理器工厂
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
public class LabelHandlerFactory {
    private static final Map<Integer, AbstractLabelHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractLabelHandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }

    public static AbstractLabelHandler getStrategy(Integer code) {
        AbstractLabelHandler strategy = STRATEGY_MAP.get(code);
        if (strategy == null) {
            throw new ScriptException(ScriptErrorEnum.SYNTAX_TREE_ERROR, "语法树中的标签类型错误");
        }
        return strategy;
    }
}
