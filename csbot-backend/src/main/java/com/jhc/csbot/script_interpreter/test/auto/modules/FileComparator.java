package com.jhc.csbot.script_interpreter.test.auto.modules;

import com.jhc.csbot.script_interpreter.test.auto.config.TestAutomationConfig;
import com.jhc.csbot.script_interpreter.test.auto.enums.GenerateDirEnum;
import com.jhc.csbot.script_interpreter.test.auto.enums.TestResultEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 文件比较器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/26
 */
@Component
@Slf4j
public class FileComparator {
    @Resource
    private TestAutomationConfig testAutomationConfig;

    @Resource
    private TestResultWriter testResultWriter;

    /**
     * 比较输出文件与预期文件
     */
    public void exec() {
        log.info("正在比较输出文件与预期文件 ...");
        switch (testAutomationConfig.getCaseType()) {
            case "normal":
                // 比较正常测试
                compareNormalTest();
                break;
            case "abnormal":
                // 比较异常测试
                compareAbnormalTest();
                break;
            default:
                throw new RuntimeException("测试用例类型错误");
        }
        log.info("比较输出文件与预期文件完成");
    }

    /**
     * 比较两个文件内容是否一致
     * @param currentTestGenerateFile
     * @param currentTestExpectFile
     * @return
     */
    public boolean compare(String currentTestGenerateFile, String currentTestExpectFile) {
        try {
            String generateFileContent = Files.readString(Path.of(currentTestGenerateFile));
            String expectFileContent = Files.readString(Path.of(currentTestExpectFile));
            // 去掉换行符
            generateFileContent = generateFileContent.replaceAll("\n", "");
            expectFileContent = expectFileContent.replaceAll("\n", "");
            // 去掉空格
            generateFileContent = generateFileContent.replaceAll(" ", "");
            expectFileContent = expectFileContent.replaceAll(" ", "");
            // 去掉 /r
            generateFileContent = generateFileContent.replaceAll("\r", "");
            expectFileContent = expectFileContent.replaceAll("\r", "");
            return generateFileContent.startsWith(expectFileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 比较异常测试
     */
    private void compareAbnormalTest() {
        // 1.拿到正常测试的输出文件 以及 预期的输出文件
        String normalGenerateDir = GenerateDirEnum.ABNORMAL.getDir();
        String normalExpectDir = testAutomationConfig.getCaseDir();

        // 2.从测试集 1 开始一直往后比较, 如果发现不一致就记录到原因
        Set<Integer> errorTestSet = new HashSet<>();
        for (int i = 1; ; i++) {
            // 2.1得到当前测试生成文件
            String currentTestGenerateFile = normalGenerateDir + "/t" + i + ".result";
            // 2.2得到当前测试预期文件
            String currentTestExpectFile = normalExpectDir + "/t" + i + "/t" + i + ".result";

            // 2.3判断当前测试生成文件是否存在
            if (!new File(currentTestGenerateFile).exists()) {
                // 5.如果不存在, 则退出循环
                break;
            }

            // 2.4比较当前测试生成文件与当前测试预期文件
            if (!compare(currentTestGenerateFile, currentTestExpectFile)) {
                errorTestSet.add(i);
            }
        }

        // 3.将错误的测试集写入到错误集合中
        if (errorTestSet.isEmpty()) {
            testResultWriter.record(TestResultEnum.SUCCESS, null);
        } else {
            String reason = "错误的测试集: " + errorTestSet.toString();
            testResultWriter.record(TestResultEnum.FAIL, reason);
        }
    }

    /**
     * 比较正常测试
     */
    private void compareNormalTest() {
        // 1.拿到正常测试的输出文件 以及 预期的输出文件
        String normalGenerateDir = GenerateDirEnum.NORMAL.getDir();
        String normalExpectDir = testAutomationConfig.getCaseDir();

        // 2.从测试集 1 开始一直往后比较, 如果发现不一致就记录到原因
        Set<Integer> errorTestSet = new HashSet<>();
        for (int i = 1; ; i++) {
            // 2.1得到当前测试生成文件
            String currentTestGenerateFile = normalGenerateDir + "/t" + i + ".output";
            // 2.2得到当前测试预期文件
            String currentTestExpectFile = normalExpectDir + "/t" + i + "/t" + i + ".output";

            // 2.3判断当前测试生成文件是否存在
            if (!new File(currentTestGenerateFile).exists()) {
                // 5.如果不存在, 则退出循环
                break;
            }

            // 2.4比较当前测试生成文件与当前测试预期文件
            if (!compare(currentTestGenerateFile, currentTestExpectFile)) {
                errorTestSet.add(i);
            }
        }

        // 3.将错误的测试集写入到错误集合中
        if (errorTestSet.isEmpty()) {
            testResultWriter.record(TestResultEnum.SUCCESS, null);
        } else {
            String reason = "错误的测试集: " + errorTestSet.toString();
            testResultWriter.record(TestResultEnum.FAIL, reason);
        }
    }
}
