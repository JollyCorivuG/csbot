package com.jhc.csbot.script_interpreter.common.domain.model.variable.common;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 环境变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.STATUS;
    }

    @Data
    @Accessors(chain = true)
    public static class Option {
        private String input;
        private String text;
        private Intent purpose;
    }


    private Boolean init;
    private String speak;
    private List<Option> options = new ArrayList<>();
}
