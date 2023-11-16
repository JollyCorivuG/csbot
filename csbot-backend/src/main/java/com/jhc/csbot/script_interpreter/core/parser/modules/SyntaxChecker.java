package com.jhc.csbot.script_interpreter.core.parser.modules;

import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeInfo;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.core.parser.strategy.checker.AbstractKeywordChecker;
import com.jhc.csbot.script_interpreter.core.parser.strategy.checker.KeywordCheckerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @Description: 语法检查器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
public class SyntaxChecker {

    /**
     * 检查一级节点, 因为一级节点是关键词, 故根据关键词去分类进行相应的检查
     * @param key
     * @param value
     */
    private static void checkFirstLevelNode(String key, SyntaxTreeNode value, Set<String> variables) {
        AbstractKeywordChecker checker = KeywordCheckerFactory.getStrategy(key);
        checker.check(value, variables);
    }

    public static void exec(SyntaxTreeInfo syntaxTreeInfo) {
        for (Map.Entry<String, SyntaxTreeNode> entry: syntaxTreeInfo.getRoot().getChildren().entrySet()) {
            checkFirstLevelNode(entry.getKey(), entry.getValue(), syntaxTreeInfo.getVariables());
        }
    }
}
