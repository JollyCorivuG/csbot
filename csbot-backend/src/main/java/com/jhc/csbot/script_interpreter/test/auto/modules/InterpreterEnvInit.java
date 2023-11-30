package com.jhc.csbot.script_interpreter.test.auto.modules;

import com.jhc.csbot.script_interpreter.core.interpreter.ScriptInterpreter;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.UsersState;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import com.jhc.csbot.script_interpreter.core.lexer.ScriptLexer;
import com.jhc.csbot.script_interpreter.core.parser.ScriptParser;

/**
 * @Description: 解释器环境初始化器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/26
 */
public class InterpreterEnvInit {
    /**
     * 初始化解释器环境
     * @param scriptPath
     */
    public static void exec(String scriptPath) {
        // 1.清除用户状态和变量表
        UsersState.clear();
        VariableTable.clear();

        // 2.加载脚本
        ScriptLexer scriptLexer = new ScriptLexer(scriptPath); // 词法分析器
        scriptLexer.init();
        if (TestActuator.errorSet.contains(TestActuator.curTestGenerateFile)) {
            return;
        }
        ScriptParser scriptParser = new ScriptParser(scriptLexer.getTokenStream()); // 语法分析器
        if (TestActuator.errorSet.contains(TestActuator.curTestGenerateFile)) {
            return;
        }
        scriptParser.init();
        if (TestActuator.errorSet.contains(TestActuator.curTestGenerateFile)) {
            return;
        }
        ScriptInterpreter scriptInterpreter = new ScriptInterpreter(scriptParser.getSyntaxTree()); // 解释器
        scriptInterpreter.init();
    }
}
