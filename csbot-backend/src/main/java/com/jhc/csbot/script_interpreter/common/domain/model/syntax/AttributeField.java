package com.jhc.csbot.script_interpreter.common.domain.model.syntax;

import com.jhc.csbot.script_interpreter.common.domain.enums.syntax.AttributeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 属性字段
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeField {
    private String name; // 属性名
    private List<AttributeTypeEnum> validTypes; // 有效类型
    private Boolean isRequired; // 是否必须
}
