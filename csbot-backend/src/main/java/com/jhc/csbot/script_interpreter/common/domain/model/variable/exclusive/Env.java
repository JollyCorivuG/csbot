package com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;

/**
 * @Description: 环境变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Env extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.ENV;
    }

    // 捕获了哪个 java 类
    private Class<?> clazz;
}
