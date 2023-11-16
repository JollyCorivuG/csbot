package com.jhc.csbot.script_interpreter.common.domain.model.syntax;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Description: 语法树信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyntaxTreeInfo {
    private SyntaxTreeNode root;
    Set<String> variables;

    /**
     * 打印整个语法树的信息
     * @param root
     */
    public void showTotalInfo() {
        showNodeInfo(root);
    }

    private void showNodeInfo(SyntaxTreeNode root) {
        // 递归打印节点信息, 打印 token 的 type 和 value
        System.out.println(root.getToken().getType() + " " + root.getToken().getValue() + " " + root.getLeftPartOfOperator());
        for (SyntaxTreeNode node: root.getChildren().values()) {
            showNodeInfo(node);
        }
    }
}
