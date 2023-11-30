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

    private String table; // 表名
    Map<String, Map.Entry<AbstractVariable, String>> filters; // 过滤条件, key 为字段名, value 为过滤条件
    private String field; // 字段名
}
