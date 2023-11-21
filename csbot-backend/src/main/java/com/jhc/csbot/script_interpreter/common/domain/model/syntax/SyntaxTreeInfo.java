package com.jhc.csbot.script_interpreter.common.domain.model.syntax;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
@Slf4j
public class SyntaxTreeInfo {
    private SyntaxTreeNode root;
    Set<String> variables;

    /**
     * 打印整个语法树的信息
     * @param root
     */
    public void showTotalInfo() {
        StringBuilder info = new StringBuilder();
        getNodeLevel(root, 0, info);
        putInfoToFile(info.toString());
    }

    private void putInfoToFile(String info) {
        // 1.输出文件路径
        String filePath = "src/main/resources/syntax_tree_info.txt";

        // 2.将 info 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(info);
        } catch (IOException e) {
            log.error("语法树信息写入文件失败");
        }
    }


    /**
     * 获取语法树的每一层的节点
     * @param root
     * @param level
     * @param levelMap
     */
    private void getNodeLevel(SyntaxTreeNode root, int level, StringBuilder info) {
        //根据 level 层级来打印
        info.append("  ".repeat(Math.max(0, level)));
        if (level != 0) {
            info.append("|---");
        }
        info.append(root.toString());
        info.append("\n");
        for (SyntaxTreeNode child : root.getChildren().values()) {
            getNodeLevel(child, level + 1, info);
        }
    }
}
