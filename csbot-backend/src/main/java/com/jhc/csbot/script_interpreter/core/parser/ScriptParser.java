package com.jhc.csbot.script_interpreter.core.parser;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.SyntaxTreeLabelEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTree;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Description: 脚本解析器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ScriptParser {
    private String scriptPath;

    private SyntaxTreeNode newSyntaxTreeNode(SyntaxTreeLabelEnum label, String lexical, String value) {
        return SyntaxTreeNode.builder()
                .label(label)
                .lexical(lexical)
                .value(value)
                .children(new HashMap<>())
                .build();
    }

    /**
     * 构建语法树
     * @param scriptPath
     * @return
     */
    private SyntaxTreeNode buildSyntaxTree(String scriptPath) {
        // 1.预处理将文件中的内容转换为字符串
        String script = ParserPreHandler.exec(scriptPath);
        System.out.println(script);

        // 2.得到语法树的根节点
        SyntaxTreeNode rootNode = newSyntaxTreeNode(SyntaxTreeLabelEnum.START_FLAG, "script", null);

        // 3.构建语法树
        int index = 0;
        while (index < script.length()) {
            // 3.1得到标识符
            StringBuilder identifier = new StringBuilder();
            while (index < script.length() && script.charAt(index) != ' ') {
                identifier.append(script.charAt(index));
                index++;
            }
            if (index == script.length()) {
                break;
            }
            index++;

            SyntaxTreeNode identifierNode;
            if (rootNode.getChildren().get(identifier.toString()) != null) {
                identifierNode = rootNode.getChildren().get(identifier.toString());
            } else {
                identifierNode = newSyntaxTreeNode(SyntaxTreeLabelEnum.IDENTIFIER, identifier.toString(), null);
                rootNode.getChildren().put(identifier.toString(), identifierNode);
            }

            // 3.2得到变量名
            StringBuilder variableName = new StringBuilder();
            while (index < script.length() && script.charAt(index) != '{') {
                if (script.charAt(index) != ' ') {
                    variableName.append(script.charAt(index));
                }
                index++;
            }
            if (index == script.length()) {
                break;
            }
            index++;
            SyntaxTreeNode variableNameNode;
            if (identifierNode.getChildren().get(variableName.toString()) != null) {
                throw new ScriptException(ScriptErrorEnum.VARIABLE_REPEATED_DEFINITION);
            } else {
                variableNameNode = newSyntaxTreeNode(SyntaxTreeLabelEnum.VARIABLE_NAME, variableName.toString(), null);
                identifierNode.getChildren().put(variableName.toString(), variableNameNode);
            }

            // 3.3得到属性, 属性可能有多个, 因此得循环读取, 每一个属性的格式为 key: value, 以 ; 分隔
            while (index < script.length() && script.charAt(index) != '}') {
                // 3.3.1得到属性的 key
                StringBuilder attributeKey = new StringBuilder();
                while (index < script.length() && script.charAt(index) != ':') {
                    if (script.charAt(index) != ' ') {
                        attributeKey.append(script.charAt(index));
                    }
                    index++;
                }
                if (index == script.length()) {
                    break;
                }
                index++;
                // 3.3.2得到属性的 value
                StringBuilder attributeValue = new StringBuilder();
                Stack<Character> stk = new Stack<>();
                while ((index < script.length() && script.charAt(index) != ';') || !stk.isEmpty()) {
                    // TODO 如果 attributeValue 不是空 或者 时空, 但是 script.charAt(index) 不是空

                    if (script.charAt(index) != ' ') {
                        if (script.charAt(index) == '{') {
                            stk.push('{');
                        } else if (script.charAt(index) == '}') {
                            if (stk.isEmpty()) {
                                log.error("脚本语法错误, 请检查脚本语法");
                                throw new RuntimeException("脚本语法错误, 请检查脚本语法");
                            } else {
                                stk.pop();
                            }
                        }
                        attributeValue.append(script.charAt(index));
                    }
                    index++;
                }
                if (index == script.length()) {
                    break;
                }
                index++;
                if (variableNameNode.getChildren().get(attributeKey.toString()) != null) {
                    // 属性重复, 报错
                    throw new ScriptException(ScriptErrorEnum.ATTRIBUTE_REPEATED_DEFINITION);
                } else {
                    SyntaxTreeNode attributeNode = newSyntaxTreeNode(SyntaxTreeLabelEnum.ATTRIBUTE, attributeKey.toString(), attributeValue.toString());
                    variableNameNode.getChildren().put(attributeKey.toString(), attributeNode);
                }
                if (index < script.length() && script.charAt(index) == ' ') {
                    index++;
                }
            }
            index++;
            if (index < script.length() && script.charAt(index) == ' ') {
                index++;
            }
        }

        // 4.返回语法树的根节点
        return rootNode;
    }

    @PostConstruct
    public void init() {
        // 1.读取脚本文件并构建语法树
        SyntaxTreeNode syntaxTree = buildSyntaxTree(scriptPath);
        log.info("语法树构建完成, 其结构如下: ");
        SyntaxTree.showTotalInfo(syntaxTree);

        // 2.对语法树进行初步的语法检查
        SyntaxTree.check(syntaxTree);
        log.info("语法树初步语法检查通过");
    }
}
