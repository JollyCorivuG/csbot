package com.jhc.csbot.script_interpreter.common.domain.model.syntax;

import com.jhc.csbot.script_interpreter.common.domain.model.lexical.LexicalToken;
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
    private LexicalToken token;
    private Map<String, SyntaxTreeNode> children; // 子节点, key 为词汇, value 为节点

    /**
     * 额外的信息
     */
    private Boolean leftPartOfOperator; // 是否为运算符的左部

    @Override
    public String toString() {
        // 只需要打印 token 的值, 包括词汇和属性
        return token.toString();
    }
}
