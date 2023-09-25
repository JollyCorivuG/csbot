package com.jhc.csbot.model.dto.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 文本信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextMsg {
    private String content;
}
