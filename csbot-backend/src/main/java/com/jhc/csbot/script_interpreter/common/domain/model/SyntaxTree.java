package com.jhc.csbot.script_interpreter.common.domain.model;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.core.parser.handler.label.AbstractLabelHandler;
import com.jhc.csbot.script_interpreter.core.parser.handler.label.LabelHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description: 语法树
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Slf4j
public class SyntaxTree {
    /**
     * 打印整个语法树的信息
     * @param root
     */
    public static void showTotalInfo(SyntaxTreeNode root) {
        showNodeInfo(root);
    }

    public static void showNodeInfo(SyntaxTreeNode root) {
        if (root.getLabel() == SyntaxTreeLabelEnum.ATTRIBUTE) {
            log.info("节点标签: " + root.getLabel().getDesc() + ", 节点词汇: " + root.getLexical() + ", 节点值: " + root.getValue());
        } else {
            log.info("节点标签: " + root.getLabel().getDesc() + ", 节点词汇: " + root.getLexical());
        }
        for (SyntaxTreeNode node : root.getChildren().values()) {
            showNodeInfo(node);
        }
    }


    /**
     * 检查语法树的信息
     * @param syntaxTree
     */
    public static void check(SyntaxTreeNode syntaxTree) {
        for (Map.Entry<String, SyntaxTreeNode> entry: syntaxTree.getChildren().entrySet()) {
            checkNode(entry.getValue(), entry.getKey());
        }
    }

    private static void checkNode(SyntaxTreeNode node, String identifier) {
        AbstractLabelHandler labelHandler = LabelHandlerFactory.getStrategy(node.getLabel().getType());
        labelHandler.check(node, identifier);
        for (Map.Entry<String, SyntaxTreeNode> entry: node.getChildren().entrySet()) {
            checkNode(entry.getValue(), identifier);
        }
    }
}
