package com.jhc.csbot.script_interpreter.core.parser.strategy.checker;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.syntax.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.AttributeField;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.utils.AttributeUtils;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 状态检查器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class StatusChecker extends AbstractKeywordChecker {
    private final List<AttributeField> attributes = Arrays.asList(
            AttributeField.builder()
                    .name("init")
                    .validTypes(List.of(AttributeTypeEnum.BOOLEAN))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("speak")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(false)
                    .build(),
            AttributeField.builder()
                    .name("option")
                    .validTypes(List.of(AttributeTypeEnum.OBJECT_ARRAY))
                    .isRequired(false)
                    .build()
    );

    private final List<AttributeField> optionAttributes = Arrays.asList(
            AttributeField.builder()
                    .name("input")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("text")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("purpose")
                    .validTypes(List.of(AttributeTypeEnum.VARIABLE))
                    .isRequired(true)
                    .build()
    );

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.STATUS;
    }

    @Override
    public void check(SyntaxTreeNode node, Set<String> variables) {
        for (SyntaxTreeNode child: node.getChildren().values()) {
            checkSingle(child, variables);
        }
    }

    private void checkObjectAttribute(SyntaxTreeNode node, Set<String> variables) {
        Set<String> appear = new HashSet<>();
        for (SyntaxTreeNode child: node.getChildren().values()) {
            String attributeName = child.getToken().getValue();
            if (optionAttributes.stream().noneMatch(attributeField -> attributeField.getName().equals(attributeName))) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, child.getToken().getLine(), "status 类型的变量中数组 option 的元素不含有属性 " + attributeName);
            }
            switch (attributeName) {
                case "input" -> {
                    appear.add("input");
                    if (!AttributeUtils.isString(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中数组 option 的元素属性 input 的类型不正确, 应为 string");
                    }
                }
                case "text" -> {
                    appear.add("text");
                    if (!AttributeUtils.isString(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中数组 option 的元素属性 text 的类型不正确, 应为 string");
                    }
                }
                case "purpose" -> {
                    appear.add("purpose");
                    if (!AttributeUtils.isVariable(child, variables)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中数组 option 的元素属性 purpose 的类型不正确, 应为 variable");
                    }

                }
            }
        }

        // 将 attributes 中所有 isRequired 为 true 的属性都出现过
        for (AttributeField attributeField: optionAttributes) {
            if (attributeField.getIsRequired() && !appear.contains(attributeField.getName())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "status 类型的变量中数组 option 的元素中缺少属性 " + attributeField.getName());
            }
        }
    }

    private void checkSingle(SyntaxTreeNode node, Set<String> variables) {
        // 遍历子节点, 看属性名称是否在 attributes 中, 并且判断类型是否正确
        Set<String> appear = new HashSet<>();
        for (SyntaxTreeNode child: node.getChildren().values()) {
            String attributeName = child.getToken().getValue();
            if (attributes.stream().noneMatch(attributeField -> attributeField.getName().equals(attributeName))) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, child.getToken().getLine(), "status 类型的变量中不含有属性 " + attributeName);
            }
            switch (attributeName) {
                case "init" -> {
                    appear.add("init");
                    if (!AttributeUtils.isBoolean(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中属性 init 的类型不正确, 应为 boolean");
                    }
                }
                case "speak" -> {
                    appear.add("speak");
                    if (!AttributeUtils.isString(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中属性 speak 的类型不正确, 应为 string");
                    }
                }
                case "option" -> {
                    appear.add("option");
                    if (!AttributeUtils.isObjectArray(child)) {
                        ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "status 类型的变量中属性 option 的类型不正确, 应为 object_array");
                    }
                    // 看对象的属性是否满足optionAttributes
                    for (SyntaxTreeNode childChild: child.getChildren().values()) {
                        if (!Objects.equals(childChild.getToken().getValue(), "[") && !Objects.equals(childChild.getToken().getValue(), "]")) {
                            checkObjectAttribute(childChild, variables);
                        }
                    }

                }
            }
        }

        // 将 attributes 中所有 isRequired 为 true 的属性都出现过
        for (AttributeField attributeField: attributes) {
            if (attributeField.getIsRequired() && !appear.contains(attributeField.getName())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "status 类型的变量中缺少属性 " + attributeField.getName());
            }
        }
    }
}
