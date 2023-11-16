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
 * @Description: 全局变量检查器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class GlobalChecker extends AbstractKeywordChecker {
    private final List<AttributeField> attributes = Arrays.asList(
            AttributeField.builder()
                    .name("type")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("value")
                    .validTypes(Arrays.asList(AttributeTypeEnum.STRING, AttributeTypeEnum.INT))
                    .isRequired(true)
                    .build()
    );
    private final Set<String> supportTypes = new HashSet<>(Arrays.asList("string", "int"));

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.GLOBAL;
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
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_NOT_FOUND, child.getToken().getLine(), "global 类型的变量中不含有属性 " + attributeName);
            }
            if (attributeName.equals("type")) {
                appear.add("type");
                if (!AttributeUtils.isString(child)) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getToken().getLine(), "global 类型的变量中属性 package_name 的类型不正确, 应为 string");
                }
                String type = child.getChildren().values().iterator().next().getToken().getValue();
                if (!supportTypes.contains(type)) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getChildren().values().iterator().next().getToken().getLine(), "global 类型的变量中属性 type 的值不正确, 仅支持 string 和 int, 且必须为小写");
                }
            } else if (attributeName.equals("value")) {
                appear.add("value");
            }
        }
        // 将 attributes 中所有 isRequired 为 true 的属性都出现过
        for (AttributeField attributeField: attributes) {
            if (attributeField.getIsRequired() && !appear.contains(attributeField.getName())) {
                ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_MISSING, node.getToken().getLine(), "global 类型的变量中缺少属性 " + attributeField.getName());
            }
        }
        // 检查 value 的类型是否和 type 一致
        SyntaxTreeNode typeNode = node.getChildren().get("type");
        SyntaxTreeNode valueNode = node.getChildren().get("value");
        String type = typeNode.getChildren().values().iterator().next().getToken().getValue();
        if (type.equals("string") && !AttributeUtils.isString(valueNode)) {
            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, valueNode.getToken().getLine(), "global 类型的变量中属性 value 的类型不正确, 与 type 不匹配");
        }
        if (type.equals("int") && !AttributeUtils.isNumber(valueNode)) {
            ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, valueNode.getToken().getLine(), "global 类型的变量中属性 value 的类型不正确, 与 type 不匹配");
        }
    }
}
