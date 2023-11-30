package com.jhc.csbot.script_interpreter.common.domain.model.variable.common;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;

/**
 * @Description: 全局变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Global extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.GLOBAL;
    }

    private String value; // 值
}
