package com.jhc.csbot.script_interpreter.core.parser.handler.label;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import org.springframework.stereotype.Component;

/**
 * @Description: 标识符标签处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class KeywordLabelHandler extends AbstractLabelHandler {
    @Override
    SyntaxTreeLabelEnum getLabelEnum() {
        return SyntaxTreeLabelEnum.IDENTIFIER;
    }

    @Override
    public void check(SyntaxTreeNode node, String identifier) {
        // 根据 node 中含有的词汇确定是哪一个标识符
        KeywordEnum keywordEnum = KeywordEnum.of(node.getLexical());
        if (keywordEnum == null) {
            throw new ScriptException(ScriptErrorEnum.IDENTIFIER_NOT_FOUND);
        }
    }
}
