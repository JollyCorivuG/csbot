package com.jhc.csbot_interpreter_sdk.config;

import com.jhc.csbot_interpreter_sdk.client.CsBotInterpreterClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 脚本解释器客户端配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "csbot.interpreter.client")
@ComponentScan
public class CsBotInterpreterClientConfig {
    private String scriptUrl; // 脚本文件路径

    @Bean
    public CsBotInterpreterClient csBotInterpreterClient() {
        return CsBotInterpreterClient.builder()
                .scriptUrl(scriptUrl)
                .build();
    }
}
