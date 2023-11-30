package com.jhc.csbot.script_interpreter.utils;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;

import java.util.Objects;
import java.util.Set;

/**
 * @Description: 属性工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
public class AttributeUtils {

    /**
     * 判断是否是字符串
     * @param node
     * @return
     */
    public static boolean isString(SyntaxTreeNode node) {
        if (node == null) {
            return false;
        }
        if (node.getChildren().size() != 1) {
            return false;
        }
        return node.getChildren().values().stream().allMatch(child -> child.getToken().getType() == TokenTypeEnum.STRING);
    }

    /**
     * 判断是否是布尔值
     * @param child
     * @return
     */
    public static boolean isBoolean(SyntaxTreeNode child) {
        if (child == null) {
            return false;
        }
        if (child.getChildren().size() != 1) {
            return false;
        }
        return child.getChildren().values().stream().allMatch(node -> node.getToken().getType() == TokenTypeEnum.BOOLEAN);
    }

    /**
     * 判断是否是整数
     * @param node
     * @return
     */
    public static boolean isNumber(SyntaxTreeNode node) {
        if (node == null) {
            return false;
        }
        if (node.getChildren().size() != 1) {
            return false;
        }
        return node.getChildren().values().stream().allMatch(child -> child.getToken().getType() == TokenTypeEnum.NUMBER);
    }

    /**
     * 判断是否是对象
     * @param child
     * @return
     */
    public static boolean isObject(SyntaxTreeNode child) {
        if (child == null) {
            return false;
        }
        if (child.getChildren().isEmpty()) {
            return false;
        }
        for (SyntaxTreeNode node: child.getChildren().values()) {
            if (node.getChildren().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是字符串数组
     * @param child
     * @return
     */
    public static boolean isStringArray(SyntaxTreeNode child) {
        if (child == null) {
            return false;
        }
        // 子节点中包含 [, ] 以及若干个字符串
        if (child.getChildren().size() < 3) {
            return false;
        }
        if (!child.getChildren().containsKey("[")) {
            return false;
        }
        if (!child.getChildren().containsKey("]")) {
            return false;
        }
        for (SyntaxTreeNode node: child.getChildren().values()) {
            if (node.getToken().getType() != TokenTypeEnum.STRING) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是对象数组
     * @param child
     * @return
     */
    public static boolean isObjectArray(SyntaxTreeNode child) {
        if (child == null) {
            return false;
        }
        // 子节点中包含 [, ] 以及若干个对象
        if (child.getChildren().size() < 3) {
            return false;
        }
        if (!child.getChildren().containsKey("[")) {
            return false;
        }
        if (!child.getChildren().containsKey("]")) {
            return false;
        }
        for (SyntaxTreeNode node: child.getChildren().values()) {
            if (!Objects.equals(node.getToken().getValue(), "[") && !Objects.equals(node.getToken().getValue(), "]")) {
                if (!isObject(node)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否是变量
     * @param child
     * @param variables
     * @return
     */
    public static boolean isVariable(SyntaxTreeNode child, Set<String> variables) {
        if (child == null) {
            return false;
        }
        // 第一种情况是有一个子节点, 且是变量
        if (child.getChildren().size() == 1) {
            SyntaxTreeNode node = child.getChildren().values().iterator().next();
            if (!node.getChildren().isEmpty()) {
                return false;
            }
            if (node.getToken().getType() != TokenTypeEnum.IDENTIFIER) {
                return false;
            }
            if (!variables.contains(node.getToken().getValue())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.VARIABLE_NOT_FOUND, node.getToken().getLine(), "变量 " + node.getToken().getValue() + " 未定义");
            }
            return true;
        }
        // 第二种情况是有三个子节点, 且其中含有一个运算符.
        if (child.getChildren().size() == 3) {
            if (!child.getChildren().containsKey(".")) {
                return false;
            }
            // 遍历子节点, 看是否有一个是变量的 leftPartOfOperator 为 true
            for (SyntaxTreeNode node: child.getChildren().values()) {
                if (node.getToken().getType() == TokenTypeEnum.IDENTIFIER && node.getLeftPartOfOperator()) {
                    if (!variables.contains(node.getToken().getValue())) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.VARIABLE_NOT_FOUND, node.getToken().getLine(), "变量 " + node.getToken().getValue() + " 未定义");
                    }
                }
            }
            return true;
        }
        return false;
    }
}
