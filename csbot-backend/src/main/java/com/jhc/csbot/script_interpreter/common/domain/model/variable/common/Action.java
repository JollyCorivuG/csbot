package com.jhc.csbot.script_interpreter.common.domain.model.variable.common;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;

/**
 * @Description: 动作变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Action extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.ACTION;
    }

    private String reply;
    private Status afterStatus;
}
