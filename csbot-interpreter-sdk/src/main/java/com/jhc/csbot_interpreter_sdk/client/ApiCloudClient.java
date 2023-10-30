package com.jhc.csbot_interpreter_sdk.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户端
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiCloudClient {
    private String accessKey;
    private String secretKey;
}
