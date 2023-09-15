package com.jhc.csbot.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: Jwt 工具类测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@SpringBootTest
public class JwtUtilsTest {
    @Test
    void testGenerate() {
        // 正常生成 token
        System.out.println("得到 token 使用的 userId: 123213123");
        String token = JwtUtils.generateAToken(123213123L);
        System.out.println("解析 token 后得到的 userId: " + JwtUtils.getUIdFromToken(token));
    }
}
