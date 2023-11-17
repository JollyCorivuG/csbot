package com.jhc.csbot.script_interpreter.core.parser.modules;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.DelimiterEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.lexical.LexicalToken;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeInfo;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;

import java.util.*;

/**
 * @Description: 语法树构建器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
public class SyntaxTreeBuilder {
    private static SyntaxTreeNode newNode(LexicalToken token) {
        return SyntaxTreeNode.builder()
                .token(token)
                .children(new HashMap<>())
                .leftPartOfOperator(false)
                .build();
    }
    private static SyntaxTreeNode newNode(LexicalToken token, boolean leftPartOfOperator) {
        return SyntaxTreeNode.builder()
                .token(token)
                .children(new HashMap<>())
                .leftPartOfOperator(leftPartOfOperator)
                .build();
    }


    /**
     * 构建虚拟节点, 表示这是一个对象数组的元素
     * @param line
     * @return
     */
    private static SyntaxTreeNode newVirtualNode(Integer line) {
        return SyntaxTreeNode.builder()
                .token(LexicalToken.builder()
                        .type(null)
                        .value("object_array_element")
                        .line(line)
                        .build())
                .children(new HashMap<>())
                .build();

    }

    /**
     * 构建对象子树
     * @param objectNode
     * @param tokenStream
     */
    private static void buildObjectSonTree(SyntaxTreeNode objectNode, List<LexicalToken> tokenStream) {
        if (tokenStream.size() == 1) {
            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.OBJECT_EMPTY, tokenStream.get(0).getLine(), "对象为空");
        }
        int index = 1;
        while (index < tokenStream.size()) {
            LexicalToken attributeNameToken = tokenStream.get(index);
            if (attributeNameToken.getType() != TokenTypeEnum.IDENTIFIER) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, attributeNameToken.getLine(), "不是预期的词法, " + attributeNameToken.getValue() + " 不是标识符");
            }
            SyntaxTreeNode attributeNameNode = objectNode.getChildren().get(attributeNameToken.getValue());
            if (attributeNameNode != null) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_REPEATED_DEFINITION, attributeNameToken.getLine(), "属性 " + attributeNameToken.getValue() + " 重复定义");
            }
            attributeNameNode = newNode(attributeNameToken);
            objectNode.getChildren().put(attributeNameToken.getValue(), attributeNameNode);
            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "不完整的对象定义");
            }
            LexicalToken colonToken = tokenStream.get(index);
            if (colonToken.getType() != TokenTypeEnum.DELIMITER || !colonToken.getValue().equals(DelimiterEnum.COLON.getDelimiter())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, colonToken.getLine(), "不是预期的词法, " + colonToken.getValue() + " 不是冒号");
            }
            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "不完整的对象定义");
            }
            // 读取属性值
            LexicalToken nxtToken = tokenStream.get(index);
            // 如果是数字、字符串、布尔值, 则直接作为属性值
            if (nxtToken.getType() == TokenTypeEnum.NUMBER || nxtToken.getType() == TokenTypeEnum.STRING || nxtToken.getType() == TokenTypeEnum.BOOLEAN) {
                SyntaxTreeNode attributeValueNode = newNode(nxtToken);
                attributeNameNode.getChildren().put(nxtToken.getValue(), attributeValueNode);
            } else if (nxtToken.getType() == TokenTypeEnum.IDENTIFIER) {
                // 如果是标识符, 则作为变量
                index++;
                if (index == tokenStream.size()) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "不完整的语句, 缺少语句结束符分号");
                }
                LexicalToken nxtNxtToken = tokenStream.get(index);
                if (nxtNxtToken.getType() == TokenTypeEnum.OPERATOR) {
                    attributeNameNode.getChildren().put(nxtToken.getValue(), newNode(nxtToken, true));
                    attributeNameNode.getChildren().put(nxtNxtToken.getValue(), newNode(nxtNxtToken));
                    index++;
                    if (index == tokenStream.size()) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "不完整的语句, 运算符右边缺少值");
                    }
                    LexicalToken nxtNxtNxtToken = tokenStream.get(index);
                    if (nxtNxtNxtToken.getType() == TokenTypeEnum.IDENTIFIER) {
                        attributeNameNode.getChildren().put(nxtNxtNxtToken.getValue(), newNode(nxtNxtNxtToken));
                    } else {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, nxtNxtNxtToken.getLine(), "运算符右边不是预期的词法, " + nxtNxtNxtToken.getValue() + " 不是标识符");
                    }
                } else {
                    index--;
                    attributeNameNode.getChildren().put(nxtToken.getValue(), newNode(nxtToken, false));
                }
            } else {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, nxtToken.getLine(), "对象出现未知的属性, 对象不能嵌套对象、数组等");
            }

            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "不完整的语句, 缺少语句结束符分号");
            }
            LexicalToken endToken = tokenStream.get(index);
            if (endToken.getType() != TokenTypeEnum.DELIMITER || !endToken.getValue().equals(DelimiterEnum.END_OF_STATEMENT.getDelimiter())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, endToken.getLine(), "不是预期的词法, " + endToken.getValue() + " 不是语句结束符分号");
            }
            index++;
        }
    }


    /**
     * 构建语法树
     *
     * @param rootNode
     * @param tokenStream
     * @return
     */
    private static SyntaxTreeInfo buildSyntaxTree(SyntaxTreeNode rootNode, List<LexicalToken> tokenStream) {
        int index = 0;
        Set<String> variableNames = new HashSet<>(); // 记录出现过的变量名信息
        while (index < tokenStream.size()) {
            // 1.读一个关键字
            LexicalToken keywordToken = tokenStream.get(index);
            if (keywordToken.getType() != TokenTypeEnum.KEYWORD) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, keywordToken.getLine(), "不是预期的词法, " + keywordToken.getValue() + " 不是关键字");
            }
            SyntaxTreeNode keywordNode = rootNode.getChildren().get(keywordToken.getValue().toLowerCase());
            if (keywordNode == null) {
                keywordNode = newNode(keywordToken);
                rootNode.getChildren().put(keywordToken.getValue().toLowerCase(), keywordNode);
            }

            // 2.读一个标识符, 是作为变量
            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
            }
            LexicalToken variableToken = tokenStream.get(index);
            if (variableToken.getType() != TokenTypeEnum.IDENTIFIER) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, variableToken.getLine(), "不是预期的词法, " + variableToken.getValue() + " 不是标识符");
            }
            if (variableNames.contains(variableToken.getValue())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.VARIABLE_REPEATED_DEFINITION, variableToken.getLine(), "变量 " + variableToken.getValue() + " 重复定义");
            }
            variableNames.add(variableToken.getValue());
            SyntaxTreeNode variableNode = newNode(variableToken);
            keywordNode.getChildren().put(variableToken.getValue(), variableNode);

            // 3.读一个左括号
            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
            }
            LexicalToken leftParenthesisToken = tokenStream.get(index);
            if (leftParenthesisToken.getType() != TokenTypeEnum.DELIMITER || !leftParenthesisToken.getValue().equals(DelimiterEnum.LEFT_BRACE.getDelimiter())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, leftParenthesisToken.getLine(), "不是预期的词法, " + leftParenthesisToken.getValue() + " 不是左括号");
            }

            // 4.循环地读取属性
            index++;
            if (index == tokenStream.size()) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
            }
            while (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.RIGHT_BRACE.getDelimiter())) {
                LexicalToken attributeNameToken = tokenStream.get(index);
                if (attributeNameToken.getType() != TokenTypeEnum.IDENTIFIER) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, attributeNameToken.getLine(), "不是预期的词法, " + attributeNameToken.getValue() + " 不是标识符");
                }
                SyntaxTreeNode attributeNameNode = variableNode.getChildren().get(attributeNameToken.getValue());
                if (attributeNameNode != null) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_REPEATED_DEFINITION, attributeNameToken.getLine(), "属性 " + attributeNameToken.getValue() + " 重复定义");
                }
                attributeNameNode = newNode(attributeNameToken);
                variableNode.getChildren().put(attributeNameToken.getValue(), attributeNameNode);
                index++;
                if (index == tokenStream.size()) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
                }
                LexicalToken colonToken = tokenStream.get(index);
                if (colonToken.getType() != TokenTypeEnum.DELIMITER || !colonToken.getValue().equals(DelimiterEnum.COLON.getDelimiter())) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, colonToken.getLine(), "不是预期的词法, " + colonToken.getValue() + " 不是冒号");
                }
                index++;
                if (index == tokenStream.size()) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
                }
                // 读取属性值
                LexicalToken nxtToken = tokenStream.get(index);
                // 如果是数字、字符串、布尔值, 则直接作为属性值
                if (nxtToken.getType() == TokenTypeEnum.NUMBER || nxtToken.getType() == TokenTypeEnum.STRING || nxtToken.getType() == TokenTypeEnum.BOOLEAN) {
                    SyntaxTreeNode attributeValueNode = newNode(nxtToken);
                    attributeNameNode.getChildren().put(nxtToken.getValue(), attributeValueNode);
                } else if (nxtToken.getType() == TokenTypeEnum.IDENTIFIER) {
                    // 如果是标识符, 则作为变量
                    index++;
                    if (index == tokenStream.size()) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
                    }
                    LexicalToken nxtNxtToken = tokenStream.get(index);
                    if (nxtNxtToken.getType() == TokenTypeEnum.OPERATOR) {
                        attributeNameNode.getChildren().put(nxtToken.getValue(), newNode(nxtToken, true));
                        attributeNameNode.getChildren().put(nxtNxtToken.getValue(), newNode(nxtNxtToken));
                        index++;
                        if (index == tokenStream.size()) {
                            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句, 运算符右边缺少值");
                        }
                        LexicalToken nxtNxtNxtToken = tokenStream.get(index);
                        if (nxtNxtNxtToken.getType() == TokenTypeEnum.IDENTIFIER) {
                            attributeNameNode.getChildren().put(nxtNxtNxtToken.getValue(), newNode(nxtNxtNxtToken));
                        } else {
                            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, nxtNxtNxtToken.getLine(), "运算符右边不是预期的词法, " + nxtNxtNxtToken.getValue() + " 不是标识符");
                        }
                    } else {
                        attributeNameNode.getChildren().put(nxtToken.getValue(), newNode(nxtToken, false));
                        index--;
                    }
                } else if (nxtToken.getType() == TokenTypeEnum.DELIMITER && nxtToken.getValue().equals(DelimiterEnum.LEFT_BRACE.getDelimiter())) {
                    // 如果是左括号, 则视为一个对象
                    List<LexicalToken> tmpStream = new ArrayList<>();
                    while (index < tokenStream.size() && (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.RIGHT_BRACE.getDelimiter()))) {
                        tmpStream.add(tokenStream.get(index));
                        index++;
                        if (index == tokenStream.size()) {
                            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tokenStream.get(index - 1).getLine(), "对象未闭合");
                        }
                    }
                    buildObjectSonTree(attributeNameNode, tmpStream);

                } else if (nxtToken.getType() == TokenTypeEnum.DELIMITER && nxtToken.getValue().equals(DelimiterEnum.LEFT_MIDDLE_BRACKET.getDelimiter())) {
                    // 如果是左中括号, 则作为数组
                    attributeNameNode.getChildren().put(nxtToken.getValue(), newNode(nxtToken));
                    index++;
                    if (index == tokenStream.size()) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                    }
                    LexicalToken nxtNxtToken = tokenStream.get(index);
                    // 如果是右中括号, 则直接结束
                    if (nxtNxtToken.getType() == TokenTypeEnum.DELIMITER && nxtNxtToken.getValue().equals(DelimiterEnum.RIGHT_MIDDLE_BRACKET.getDelimiter())) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_EMPTY, nxtNxtToken.getLine(), "数组为空");
                    }
                    if (nxtNxtToken.getType() == TokenTypeEnum.STRING) { // 如果是字符串数组
                        attributeNameNode.getChildren().put(nxtNxtToken.getValue(), newNode(nxtNxtToken));
                        index++;
                        if (index == tokenStream.size()) {
                            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                        }
                        while (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.RIGHT_MIDDLE_BRACKET.getDelimiter())) {
                            if (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.COMMA.getDelimiter())) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_NOT_CORRECT_ELEMENT_DELIMITER, tokenStream.get(index).getLine(), tokenStream.get(index).getValue() + " 不是正确的数组元素分割符");
                            }
                            index++;
                            if (index == tokenStream.size()) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                            }
                            if (tokenStream.get(index).getType() != TokenTypeEnum.STRING) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_ELEMENT_TYPE_INCONSISTENCY, tokenStream.get(index).getLine(), "数组元素类型不一致");
                            }
                            attributeNameNode.getChildren().put(tokenStream.get(index).getValue(), newNode(tokenStream.get(index)));
                            index++;
                            if (index == tokenStream.size()) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                            }
                        }
                        attributeNameNode.getChildren().put(tokenStream.get(index).getValue(), newNode(tokenStream.get(index)));
                    } else if (nxtNxtToken.getType() == TokenTypeEnum.DELIMITER && nxtNxtToken.getValue().equals(DelimiterEnum.LEFT_BRACE.getDelimiter())) { // 如果是对象数组
                        int sonIndex = 1;
                        while (true) {
                            // 一直去找右大括号, 将 token 放进一个 tmpStream 流中
                            List<LexicalToken> tmpStream = new ArrayList<>();
                            while (index < tokenStream.size() && (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.RIGHT_BRACE.getDelimiter()))) {
                                tmpStream.add(tokenStream.get(index));
                                index++;
                                if (index == tokenStream.size()) {
                                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, tmpStream.get(index - 1).getLine(), "对象未闭合");
                                }
                            }
                            // 先创建一个虚拟节点, 表示这是一个对象数组的元素
                            SyntaxTreeNode virtualNode = newVirtualNode(tmpStream.get(0).getLine());
                            attributeNameNode.getChildren().put(virtualNode.getToken().getValue() + "_" + sonIndex, virtualNode);
                            sonIndex++;
                            buildObjectSonTree(virtualNode, tmpStream);
                            index++;
                            if (index == tokenStream.size()) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                            }
                            // 如果是右中括号, 则结束
                            if (tokenStream.get(index).getType() == TokenTypeEnum.DELIMITER && tokenStream.get(index).getValue().equals(DelimiterEnum.RIGHT_MIDDLE_BRACKET.getDelimiter())) {
                                attributeNameNode.getChildren().put(tokenStream.get(index).getValue(), newNode(tokenStream.get(index)));
                                break;
                            }
                            // 如果不是逗号
                            if (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.COMMA.getDelimiter())) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_NOT_CORRECT_ELEMENT_DELIMITER, tokenStream.get(index).getLine(), tokenStream.get(index).getValue() + " 不是正确的数组元素分割符");
                            }
                            index++;
                            if (index == tokenStream.size()) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "数组未闭合");
                            }
                            if (tokenStream.get(index).getType() != TokenTypeEnum.DELIMITER || !tokenStream.get(index).getValue().equals(DelimiterEnum.LEFT_BRACE.getDelimiter())) {
                                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_ELEMENT_TYPE_INCONSISTENCY, tokenStream.get(index).getLine(), "数组元素类型不一致");
                            }
                        }
                    } else {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ARRAY_TYPE_NOT_SUPPORTED, nxtNxtToken.getLine(), "不支持的数组类型");
                    }
                } else {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, nxtToken.getLine(), "出现未知的属性");
                }

                index++;
                if (index == tokenStream.size()) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
                }
                LexicalToken endToken = tokenStream.get(index);
                if (endToken.getType() != TokenTypeEnum.DELIMITER || !endToken.getValue().equals(DelimiterEnum.END_OF_STATEMENT.getDelimiter())) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.UNEXPECTED_TOKEN, endToken.getLine(), "不是预期的词法, " + endToken.getValue() + " 不是语句结束符分号");
                }
                index++;
                if (index == tokenStream.size()) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NO_COMPLETE_STATEMENT, keywordToken.getLine(), "不完整的语句");
                }
            }
            index++;
        }
        return SyntaxTreeInfo.builder()
                .root(rootNode)
                .variables(variableNames)
                .build();
    }


    public static SyntaxTreeInfo exec(List<LexicalToken> tokenStream) {
        // 1.先创建一个伪根节点
        SyntaxTreeNode rootNode = newNode(LexicalToken.builder()
                .type(null)
                .value("script")
                .line(0)
                .build());
        return buildSyntaxTree(rootNode, tokenStream);
    }
}
