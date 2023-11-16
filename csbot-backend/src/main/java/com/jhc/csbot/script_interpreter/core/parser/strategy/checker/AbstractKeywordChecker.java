package com.jhc.csbot.script_interpreter.core.parser.strategy.checker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import jakarta.annotation.PostConstruct;

import java.util.Set;

/**
 * @Description: 关键词检查器抽象类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
public abstract class AbstractKeywordChecker {
    @PostConstruct
    private void init() {
        KeywordCheckerFactory.register(getKeywordEnum().getKeyword(), this);
    }

    /**
     * 获取关键词枚举
     * @return
     */
    abstract KeywordEnum getKeywordEnum();


    /**
     * 根据关键词检查语法树节点
     * @param node
     */
    public abstract void check(SyntaxTreeNode node, Set<String> variables);
}
