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
 * @Description: 意图检查器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class IntentChecker extends AbstractKeywordChecker {
    private final List<AttributeField> attributes = Arrays.asList(
            AttributeField.builder()
                    .name("pattern")
                    .validTypes(List.of(AttributeTypeEnum.STRING_ARRAY))
                    .isRequired(false)
                    .build(),
            AttributeField.builder()
                    .name("include")
                    .validTypes(List.of(AttributeTypeEnum.STRING_ARRAY))
                    .isRequired(false)
                    .build(),
            AttributeField.builder()
                    .name("priority")
                    .validTypes(List.of(AttributeTypeEnum.INT))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("exec_action")
                    .validTypes(List.of(AttributeTypeEnum.VARIABLE))
                    .isRequired(true)
                    .build()
    );

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.INTENT;
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
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, child.getToken().getLine(), "intent 类型的变量中不含有属性 " + attributeName);
            }
            switch (attributeName) {
                case "pattern" -> {
                    appear.add("pattern");
                    if (AttributeUtils.isStringArray(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "intent 类型的变量中属性 pattern 的类型不正确, 应为 string_array");
                    }
                }
                case "include" -> {
                    appear.add("include");
                    if (AttributeUtils.isStringArray(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "intent 类型的变量中属性 include 的类型不正确, 应为 string_array");
                    }
                }
                case "priority" -> {
                    appear.add("priority");
                    if (!AttributeUtils.isNumber(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "intent 类型的变量中属性 priority 的类型不正确, 应为 int");
                    }
                }
                case "exec_action" -> {
                    appear.add("exec_action");
                    if (!AttributeUtils.isVariable(child, variables)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "intent 类型的变量中属性 action 的类型不正确, 应为 variable");
                    }
                }
            }

        }
        // 将 attributes 中所有 isRequired 为 true 的属性都出现过
        for (AttributeField attributeField: attributes) {
            if (attributeField.getIsRequired() && !appear.contains(attributeField.getName())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "intent 类型的变量中缺少属性 " + attributeField.getName());
            }
        }
        // 其中 pattern 和 include 必须有一个
        if (!appear.contains("pattern") && !appear.contains("include")) {
            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "intent 类型的变量中 pattern 和 include 必须有一个, 不然无法判断用户意图");
        }
    }
}
