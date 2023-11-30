package com.jhc.csbot.script_interpreter.test.auto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 自动化测试配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/25
 */
@Configuration
@ConfigurationProperties(prefix = "script.interpreter.auto-test")
@Data
public class TestAutomationConfig {
    private String caseType; // 测试用例类型, 包含 normal 和 abnormal 两种
    private String caseDir; // 测试用例目录
    private Integer testCnt; // 测试次数
    private Integer timeout; // 超时时间, 单位为秒
    private String resultFile; // 测试结果输出文件
}
