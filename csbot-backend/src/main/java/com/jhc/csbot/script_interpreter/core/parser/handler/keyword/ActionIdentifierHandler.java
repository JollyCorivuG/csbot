package com.jhc.csbot.script_interpreter.core.parser.handler.keyword;

import com.jhc.csbot.script_interpreter.common.domain.enums.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.AttributeField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 行动标识符处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class ActionIdentifierHandler extends AbstractIdentifierHandler {
    private final List<AttributeField> attributes = Arrays.asList(
            AttributeField.builder()
                    .name("reply")
                    .validTypes(List.of(AttributeTypeEnum.STRING))
                    .isRequired(true)
                    .build(),
            AttributeField.builder()
                    .name("after_status")
                    .validTypes(List.of(AttributeTypeEnum.VARIABLE))
                    .isRequired(false)
                    .build()
    );

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.ACTION;
    }

    @Override
    List<AttributeField> getAttributes() {
        return attributes;
    }
}
