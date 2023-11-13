package com.jhc.csbot.script_interpreter.core.parser.handler.keyword;

import com.jhc.csbot.script_interpreter.common.domain.enums.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.AttributeField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 意图标识符处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class IntentIdentifierHandler extends AbstractIdentifierHandler {
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
                    .name("action")
                    .validTypes(List.of(AttributeTypeEnum.VARIABLE))
                    .isRequired(true)
                    .build()
    );

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.INTENT;
    }

    @Override
    List<AttributeField> getAttributes() {
        return attributes;
    }
}
