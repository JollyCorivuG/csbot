package com.jhc.csbot.script_interpreter.core.parser.handler.keyword;

import com.jhc.csbot.script_interpreter.common.domain.enums.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.AttributeField;
import com.jhc.csbot.script_interpreter.common.domain.model.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import com.jhc.csbot.script_interpreter.utils.AttributeUtils;
import jakarta.annotation.PostConstruct;

import java.util.List;

/**
 * @Description: 标识符处理器工厂类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
public abstract class AbstractIdentifierHandler {
    @PostConstruct
    private void init() {
        KeywordHandlerFactory.register(getKeywordEnum().getKeyword(), this);
    }

    abstract KeywordEnum getKeywordEnum();

    abstract List<AttributeField> getAttributes();

    public String attributeNotFoundMsg(String attribute) {
        return getKeywordEnum().getDesc() + " 不支持属性 " + attribute;
    }

    public String attributeTypeNotMatchMsg(String attribute) {
        return getKeywordEnum().getDesc() + " 的属性 " + attribute + " 类型不匹配";
    }

    public void check(SyntaxTreeNode node) {
        // 检查 node 中的属性是否符合要求
        List<AttributeField> attributes = getAttributes();
        String attributeName = node.getLexical();
        AttributeField attributeField = attributes.stream()
                .filter(attribute -> attribute.getName().equals(attributeName))
                .findFirst()
                .orElseThrow(() -> new ScriptException(ScriptErrorEnum.ATTRIBUTE_NOT_FOUND, attributeNotFoundMsg(attributeName)));
        // 当属性名存在时，检查属性类型是否符合要求
        AttributeTypeEnum attributeType = AttributeUtils.inferType(node.getValue());
        if (!attributeField.getValidTypes().contains(attributeType)) {
            throw new ScriptException(ScriptErrorEnum.ATTRIBUTE_TYPE_ERROR, attributeTypeNotMatchMsg(attributeName));
        }
    }
}
