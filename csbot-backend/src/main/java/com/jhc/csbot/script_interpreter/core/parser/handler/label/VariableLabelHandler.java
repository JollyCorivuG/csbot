package com.jhc.csbot.script_interpreter.core.parser.handler.label;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import org.springframework.stereotype.Component;

/**
 * @Description: 变量标签处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class VariableLabelHandler extends AbstractLabelHandler {
    @Override
    SyntaxTreeLabelEnum getLabelEnum() {
        return SyntaxTreeLabelEnum.VARIABLE_NAME;
    }

    @Override
    public void check(SyntaxTreeNode node, String identifier) {
        // 检查变量名是否符合命名规范, 命名规范是, 可以含有中文, 但不能以数字开头, 不能含有特殊字符
        String lexical = node.getLexical();
        if (lexical.matches("^[0-9].*")) {
            throw new ScriptException(ScriptErrorEnum.VARIABLE_NAME_ERROR);
        }
        if (lexical.matches(".*[^a-zA-Z0-9_一-龥].*")) {
            throw new ScriptException(ScriptErrorEnum.VARIABLE_NAME_ERROR);
        }
    }
}
