package com.jhc.csbot.script_interpreter.common.domain.model;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;

/**
 * @Description: 语法树
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
public class SyntaxTree {
    public static void showTotalInfo(SyntaxTreeNode root) {
        showNodeInfo(root);
    }

    public static void showNodeInfo(SyntaxTreeNode root) {
        if (root.getLabel() == SyntaxTreeLabelEnum.ATTRIBUTE) {
            System.out.println("节点标签: " + root.getLabel().getDesc() + ", 节点词汇: " + root.getLexical() + ", 节点值: " + root.getValue());
        } else {
            System.out.println("节点标签: " + root.getLabel().getDesc() + ", 节点词汇: " + root.getLexical());
        }
        for (SyntaxTreeNode child : root.getChildren()) {
            showNodeInfo(child);
        }
    }
}
