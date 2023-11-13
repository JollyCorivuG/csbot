package com.jhc.csbot.script_interpreter.core.parser.handler.label;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.core.parser.handler.keyword.AbstractIdentifierHandler;
import com.jhc.csbot.script_interpreter.core.parser.handler.keyword.KeywordHandlerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 属性标签处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class AttributeLabelHandler extends AbstractLabelHandler {
    @Override
    SyntaxTreeLabelEnum getLabelEnum() {
        return SyntaxTreeLabelEnum.ATTRIBUTE;
    }

    @Override
    public void check(SyntaxTreeNode node, String keyword) {
        // 根据 keyword 确定是哪一个标识符, 然后进行对应的属性检查
        AbstractIdentifierHandler keywordHandler = KeywordHandlerFactory.getStrategy(keyword);
        keywordHandler.check(node);
    }
}
