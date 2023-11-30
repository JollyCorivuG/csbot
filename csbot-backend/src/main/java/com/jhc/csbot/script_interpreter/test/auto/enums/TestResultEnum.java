package com.jhc.csbot.script_interpreter.test.auto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 测试结果枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/25
 */
@AllArgsConstructor
@Getter
public enum TestResultEnum {
    SUCCESS("成功"),
    FAIL("失败"),
    TIMEOUT("超时");

    private final String result;
}
