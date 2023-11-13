package com.jhc.csbot.script_interpreter.core.parser.handler.label;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import jakarta.annotation.PostConstruct;

/**
 * @Description: 标签处理器抽象类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
public abstract class AbstractLabelHandler {
    @PostConstruct
    private void init() {
        LabelHandlerFactory.register(getLabelEnum().getType(), this);
    }

    abstract SyntaxTreeLabelEnum getLabelEnum();

    /**
     * 检查标签的合法性
     * @param node
     */
    public abstract void check(SyntaxTreeNode node, String identifier);
}
