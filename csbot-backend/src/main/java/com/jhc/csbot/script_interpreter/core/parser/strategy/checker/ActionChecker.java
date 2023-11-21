package com.jhc.csbot.script_interpreter.core.parser.strategy.checker;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.syntax.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.AttributeField;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.utils.AttributeUtils;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 动作检查器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class ActionChecker extends AbstractKeywordChecker {
    private final List<AttributeField> attributes = Arrays.asList(
            AttributeField.builder()
                    .name("default")
                    .validTypes(List.of(AttributeTypeEnum.BOOLEAN))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("reply")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("after_status")
                    .validTypes(List.of(AttributeTypeEnum.VARIABLE))
                    .isRequired(true)
                    .build()
    );


    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.ACTION;
    }

    @Override
    public void check(SyntaxTreeNode node, Set<String> variables) {
        for (SyntaxTreeNode child: node.getChildren().values()) {
            checkSingle(child, variables);
        }
    }

    private void checkSingle(SyntaxTreeNode node, Set<String> variables) {
        // 遍历子节点, 看属性名称是否在 attributes 中, 并且判断类型是否正确
        Set<String> appear = new HashSet<>();
        for (SyntaxTreeNode child: node.getChildren().values()) {
            String attributeName = child.getToken().getValue();
            if (attributes.stream().noneMatch(attributeField -> attributeField.getName().equals(attributeName))) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, child.getToken().getLine(), "action 类型的变量中不含有属性 " + attributeName);
            }
            switch (attributeName) {
                case "default" -> {
                    appear.add("default");
                    if (!AttributeUtils.isBoolean(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "action 类型的变量中属性 default 的类型不正确, 应为 boolean");
                    }
                }
                case "reply" -> {
                    appear.add("reply");
                    if (!AttributeUtils.isString(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "action 类型的变量中属性 reply 的类型不正确, 应为 string");
                    }
                }
                case "after_status" -> {
                    appear.add("after_status");
                    if (!AttributeUtils.isVariable(child, variables)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "action 类型的变量中属性 after_status 的类型不正确, 应为 variable");
                    }
                }
            }

        }
        // 将 attributes 中所有 isRequired 为 true 的属性都出现过
        for (AttributeField attributeField: attributes) {
            if (attributeField.getIsRequired() && !appear.contains(attributeField.getName())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "action 类型的变量中缺少属性 " + attributeField.getName());
            }
        }
    }
}
