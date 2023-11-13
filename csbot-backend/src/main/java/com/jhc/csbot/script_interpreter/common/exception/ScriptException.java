package com.jhc.csbot.script_interpreter.common.exception;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.LexicalErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import lombok.Getter;

/**
 * @Description: 脚本异常类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Getter
public class ScriptException extends RuntimeException {
    private final Integer code;

    public ScriptException(ScriptErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    public ScriptException(LexicalErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    public ScriptException(ScriptErrorEnum error, String message) {
        super(message);
        this.code = error.getCode();
    }

    public ScriptException(LexicalErrorEnum error, String message) {
        super(message);
        this.code = error.getCode();
    }

}
