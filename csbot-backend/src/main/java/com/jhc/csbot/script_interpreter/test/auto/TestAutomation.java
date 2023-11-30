package com.jhc.csbot.script_interpreter.test.auto;

import com.jhc.csbot.script_interpreter.test.auto.config.TestAutomationConfig;
import com.jhc.csbot.script_interpreter.test.auto.enums.TestResultEnum;
import com.jhc.csbot.script_interpreter.test.auto.modules.FileComparator;
import com.jhc.csbot.script_interpreter.test.auto.modules.TestActuator;
import com.jhc.csbot.script_interpreter.test.auto.modules.TestFlag;
import com.jhc.csbot.script_interpreter.test.auto.modules.TestResultWriter;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 自动化测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/25
 */
@SpringBootTest
public class TestAutomation {
    @Resource
    private TestAutomationConfig testAutomationConfig;

    @Resource
    private TestResultWriter testResultWriter;

    @Resource
    private TestActuator testActuator;

    @Resource
    private FileComparator fileComparator;

    @Test
    public void exec() {
        TestFlag.isTest = true;
        // 记录初始时间
        long startTime = System.currentTimeMillis();

        // 执行测试生成输出文件
        for (int i = 0; i < testAutomationConfig.getTestCnt(); i++) {
            testActuator.start();
        }
        // 将输出文件与预期文件进行比较
        fileComparator.exec();

        // 记录结束时间
        long endTime = System.currentTimeMillis();

        // 判断是否超时
        if (endTime - startTime > testAutomationConfig.getTimeout() * 1000) {
            testResultWriter.record(TestResultEnum.TIMEOUT,  "测试超时");
        }
    }
}
