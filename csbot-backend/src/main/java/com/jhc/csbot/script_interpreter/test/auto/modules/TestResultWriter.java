package com.jhc.csbot.script_interpreter.test.auto.modules;

import com.jhc.csbot.script_interpreter.test.auto.config.TestAutomationConfig;
import com.jhc.csbot.script_interpreter.test.auto.enums.TestResultEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description: 测试结果写入器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/25
 */
@Component
public class TestResultWriter {
    @Resource
    private TestAutomationConfig testAutomationConfig;

    /**
     * 记录测试结果以及原因到结果文件中
     * @param result
     * @param reason
     */
    public void record(TestResultEnum result, String reason) {
        String outputPath = testAutomationConfig.getResultFile();
        // 第一行的格式为 测试用例类型: testAutomationConfig.getCaseType()
        // 第二行的格式为 测试次数: testAutomationConfig.getTestCnt()
        // 第三行的格式为 测试结果: result.getResult()
        // 第四行的格式为 测试原因: reason
        // 第五行的格式为 测试用例目录: testAutomationConfig.getCaseDir()
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // 写入测试用例类型
            writer.write("测试用例类型: " + testAutomationConfig.getCaseType());
            writer.newLine();

            // 写入测试次数
            writer.write("测试次数: " + testAutomationConfig.getTestCnt());
            writer.newLine();

            // 写入测试结果
            writer.write("测试结果: " + result.getResult());
            writer.newLine();

            // 写入测试原因
            if (reason != null) {
                writer.write("测试原因: " + reason);
                writer.newLine();
            }
            System.out.println("记录测试结果成功！");
        } catch (IOException e) {
            // 处理IO异常
            e.printStackTrace();
            System.out.println("记录测试结果失败：" + e.getMessage());
        }
    }
}
