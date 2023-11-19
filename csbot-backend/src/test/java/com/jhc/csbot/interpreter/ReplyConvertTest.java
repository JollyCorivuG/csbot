package com.jhc.csbot.interpreter;

import com.jhc.csbot.script_interpreter.core.interpreter.modules.ReplyConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 回复转换器测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/18
 */
@SpringBootTest
public class ReplyConvertTest {
    @Test
    void test() {
        String template = "你的账单是元, ${user.nickName}";
        System.out.println(ReplyConverter.templateString(1L, template));
    }
}
