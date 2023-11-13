package com.jhc.csbot.script_interpreter.core.parser.handler.keyword;

import com.jhc.csbot.script_interpreter.common.domain.enums.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.AttributeField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 状态标识符处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Component
public class StatusIdentifierHandler extends AbstractIdentifierHandler {
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

    @Override
    KeywordEnum getKeywordEnum() {
        return KeywordEnum.STATUS;
    }

    @Override
    List<AttributeField> getAttributes() {
        return attributes;
    }
}
