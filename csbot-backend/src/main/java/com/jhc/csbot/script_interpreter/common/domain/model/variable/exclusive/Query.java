package com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;

import java.util.Map;

/**
 * @Description: 查询变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Query extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.QUERY;
    }

    private String table;
    Map<String, Map.Entry<AbstractVariable, String>> filters;
    private String field;
}
