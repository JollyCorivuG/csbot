package com.jhc.csbot.script_interpreter.common.domain.model;

import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description: 语法树节点
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyntaxTreeNode {
    private SyntaxTreeLabelEnum label; // 节点的标签
    private String lexical; // 节点的词汇
    private String value; // 节点的值, 只有当标签为属性时才有值
    private Map<String, SyntaxTreeNode> children; // 子节点, key 为词汇, value 为节点
}
