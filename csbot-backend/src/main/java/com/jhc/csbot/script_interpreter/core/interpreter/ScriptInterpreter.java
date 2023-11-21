package com.jhc.csbot.script_interpreter.core.interpreter;

import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeInfo;
import com.jhc.csbot.script_interpreter.core.interpreter.modules.VariableClassifier;
import com.jhc.csbot.script_interpreter.core.interpreter.modules.VariableTableBuilder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 脚本解释器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ScriptInterpreter {
    private SyntaxTreeInfo syntaxTree;

    @PostConstruct
    public void init() {
        // 1.根据语法树构造变量表
        VariableTableBuilder.exec(syntaxTree.getRoot());

        // 2.将变量表分类
        VariableClassifier.exec();
    }

}
