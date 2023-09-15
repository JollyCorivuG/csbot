package com.jhc.csbot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Swagger 配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("客服机器人 API 文档")
                .description("客服机器人相关 API 文档")
                .contact(new Contact().name("江浩诚").email("1547676099@qq.com"))
                .version("v0.0.1"));
    }
}
