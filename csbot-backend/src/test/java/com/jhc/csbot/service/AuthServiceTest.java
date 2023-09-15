package com.jhc.csbot.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 权限服务测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@SpringBootTest
public class AuthServiceTest {
    @Resource
    private IAuthService authService;

    @Test
    void testGetTwoTokens() {
        System.out.println(authService.getTwoTokens(123213123L));
    }
}
