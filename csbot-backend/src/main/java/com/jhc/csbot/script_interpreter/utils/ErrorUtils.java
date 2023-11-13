package com.jhc.csbot.script_interpreter.utils;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.LexicalErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 错误工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Slf4j
public class ErrorUtils {
    public static void declareScriptError(ScriptErrorEnum error, String errorMsg) {
        log.error("Script Error: [{}]", errorMsg);
        throw new ScriptException(error, errorMsg);
    }
    public static void declareScriptError(ScriptErrorEnum error) {
        log.error("Script Error: [{}]", error.getMessage());
        throw new ScriptException(error);
    }

    public static void declareLexicalError(LexicalErrorEnum error, Integer line, String errorMsg) {
        log.error("Lexical Error: [{}] at line {}", errorMsg, line);
        throw new ScriptException(error, errorMsg);
    }
    public static void declareLexicalError(LexicalErrorEnum error, Integer line) {
        log.error("Lexical Error: [{}] at line {}", error.getMessage(), line);
        throw new ScriptException(error);
    }

}
