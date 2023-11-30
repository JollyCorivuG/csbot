package com.jhc.csbot.script_interpreter.utils;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.LexicalErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import com.jhc.csbot.script_interpreter.test.auto.modules.TestActuator;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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

    private static void recordErrorToFile(String error) {
        try {
            Files.writeString(Path.of(TestActuator.curTestGenerateFile),
                    error,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            log.error("Error while recording error to file: {}", e.getMessage());
        }
    }

    public static void declareLexicalError(LexicalErrorEnum error, Integer line, String errorMsg) {
//        log.error("Lexical Error: [{}] at line {}", errorMsg, line);
//        throw new ScriptException(error, errorMsg);
        // 如果是在自动测试环境
        String result = String.format("Lexical Error: [%s] at line %d", errorMsg, line);
        recordErrorToFile(result);
        TestActuator.errorSet.add(TestActuator.curTestGenerateFile);
    }
    public static void declareLexicalError(LexicalErrorEnum error, Integer line) {
//        log.error("Lexical Error: [{}] at line {}", error.getMessage(), line);
//        throw new ScriptException(error);
        // 如果是在自动测试环境
        String result = String.format("Lexical Error: [%s] at line %d", error.getMessage(), line);
        recordErrorToFile(result);
        TestActuator.errorSet.add(TestActuator.curTestGenerateFile);
    }

    public static void declareSyntaxError(SyntaxErrorEnum error, Integer line, String errorMsg) {
//        log.error("Syntax Error: [{}] at line {}", errorMsg, line);
//        throw new ScriptException(error, errorMsg);
        // 如果是在自动测试环境
        String result = String.format("Syntax Error: [%s] at line %d", errorMsg, line);
        recordErrorToFile(result);
        TestActuator.errorSet.add(TestActuator.curTestGenerateFile);
    }
    public static void declareSyntaxError(SyntaxErrorEnum error, Integer line) {
//        log.error("Syntax Error: [{}] at line {}", error.getMessage(), line);
//        throw new ScriptException(error);
        // 如果是在自动测试环境
        String result = String.format("Syntax Error: [%s] at line %d", error.getMessage(), line);
        recordErrorToFile(result);
        TestActuator.errorSet.add(TestActuator.curTestGenerateFile);
    }

}
