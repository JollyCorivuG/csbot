package com.jhc.csbot.script_interpreter.common.domain.model.lexical;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 词法单元
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LexicalToken {
    private TokenTypeEnum type; // 类型
    private String value; // 值
    private Integer line; // 行号
}
