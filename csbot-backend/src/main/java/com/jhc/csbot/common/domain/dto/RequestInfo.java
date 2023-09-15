package com.jhc.csbot.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: web 请求信息类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfo {
    private Long uid;
    private String ip;
}
