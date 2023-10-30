package com.jhc.csbot.interpreter;

import com.jhc.csbot_interpreter_sdk.client.CsBotInterpreterClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 脚本解释器测试类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/30
 */
@SpringBootTest
public class InterpreterTest {
    @Resource
    private CsBotInterpreterClient csBotInterpreterClient;

    @Test
    void test() {
        csBotInterpreterClient.testReadFile();
    }
}
