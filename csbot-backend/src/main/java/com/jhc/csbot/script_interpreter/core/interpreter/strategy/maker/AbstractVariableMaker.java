package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import jakarta.annotation.PostConstruct;

/**
 * @Description: 抽象变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
public abstract class AbstractVariableMaker {
    @PostConstruct
    private void init() {
        VariableMakerFactory.register(getVariableType().getKeyword(), this);
    }

    /**
     * 获取变量类型枚举
     * @return
     */
    abstract KeywordEnum getVariableType();

    /**
     * 根据语法树节点构造变量
     * @param node
     */
    public abstract void make(SyntaxTreeNode node);
}
