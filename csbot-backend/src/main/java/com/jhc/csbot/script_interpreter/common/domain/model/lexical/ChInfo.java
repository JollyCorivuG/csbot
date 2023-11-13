package com.jhc.csbot.script_interpreter.common.domain.model.lexical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 字符信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChInfo {
    private Character ch; // 字符
    private Integer line; // 行号
}
