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
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsImV4cCI6MTY5NTYzMDYzNywiaWF0IjoxNjk1NjI4ODM3fQ.2f1TfjiZ308um4S7_hfo2O0R_b0fFIjsTOQ2iP6xDDE";
        System.out.println("解析 token 后得到的 userId: " + JwtUtils.getUIdFromToken(token));
    }
}
