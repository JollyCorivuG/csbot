package com.jhc.csbot.script_interpreter.test.auto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 生成目录枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/26
 */
@AllArgsConstructor
@Getter
public enum GenerateDirEnum {
    ABNORMAL("E:/full_stack_project/csbot/csbot-backend/src/main/java/com/jhc/csbot/script_interpreter/test/auto/generate/abnormal"),
    NORMAL("E:/full_stack_project/csbot/csbot-backend/src/main/java/com/jhc/csbot/script_interpreter/test/auto/generate/normal");

    private final String dir;
}
