package com.jhc.csbot.script_interpreter.test.auto.modules;

import com.jhc.csbot.script_interpreter.test.auto.config.TestAutomationConfig;
import com.jhc.csbot.script_interpreter.test.auto.enums.GenerateDirEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 测试执行器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/25
 */
@Component
@Slf4j
public class TestActuator {
    @Resource
    private TestAutomationConfig testAutomationConfig;

    @Resource
    private DialogActuator dialogActuator;

    public static String curTestGenerateFile; // 当前测试生成文件
    public static Set<String> errorSet = new HashSet<>(); // 错误集合

    public void start() {
        // 1.得到测试目录
        String testDir = testAutomationConfig.getCaseDir();

        // 2.遍历下面的 t1, t2, t3, t4, t5, t6, t7, t8, ... 目录直到目录不存在
        for (int i = 1; ; i++) {
            log.info("正在测试目录: " + testDir + "/t" + i + " ...");

            // 2.1得到当前测试目录
            String currentTestDir = testDir + "/t" + i;

            // 2.2判断当前测试目录是否存在
            if (!new File(currentTestDir).exists()) {
                // 5.如果不存在, 则退出循环
                break;
            }

            switch (testAutomationConfig.getCaseType()) {
                case "normal":
                    // 执行正常测试
                    execNormalTest(currentTestDir);
                    break;
                case "abnormal":
                    // 执行异常测试
                    execAbnormalTest(currentTestDir);
                    break;
                default:
                    throw new RuntimeException("测试用例类型错误");
            }

            log.info("测试目录: " + testDir + "/t" + i + " 测试完成");
        }
    }

    /**
     * 执行异常测试
     */
    private void execAbnormalTest(String testDir) {
        // 1.确定测试生成文件
        TestActuator.curTestGenerateFile = GenerateDirEnum.ABNORMAL.getDir() + "/t" + testDir.charAt(testDir.length() - 1) + ".result";

        // 2.新建解释器执行环境
        // 2.1得到脚本存放路径
        String scriptPath = testDir + "/t" + testDir.charAt(testDir.length() - 1) + ".rsl";
        // 2.2重新解析脚本
        InterpreterEnvInit.exec(scriptPath);
    }

    /**
     * 执行正常测试
     */
    private void execNormalTest(String testDir) {
        // 1.确定测试生成文件 (在正常情况下, 文件内容是用户和机器人的对话)
        TestActuator.curTestGenerateFile = GenerateDirEnum.NORMAL.getDir() + "/t" + testDir.charAt(testDir.length() - 1) + ".output";

        // 2.新建解释器执行环境
        // 2.1得到脚本存放路径
        String scriptPath = testDir + "/t" + testDir.charAt(testDir.length() - 1) + ".rsl";
        // 2.2重新解析脚本
        InterpreterEnvInit.exec(scriptPath);

        // 3.进行用户和机器人的对话
        // 3.1拿到用户的输入
        String userInput = testDir + "/t" + testDir.charAt(testDir.length() - 1) + ".input";
        // 3.2进行对话
        dialogActuator.exec(userInput);
    }
}
