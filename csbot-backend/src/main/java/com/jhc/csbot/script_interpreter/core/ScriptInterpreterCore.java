package com.jhc.csbot.script_interpreter.core;

import com.jhc.csbot.script_interpreter.core.lexer.ScriptLexer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 脚本解释器核心类, 也是注入 Bean 的入口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "script.interpreter")
public class ScriptInterpreterCore {
    private String scriptPath;

    @Bean
    public ScriptLexer scriptLexer() {
        return new ScriptLexer(scriptPath);
    }

}
