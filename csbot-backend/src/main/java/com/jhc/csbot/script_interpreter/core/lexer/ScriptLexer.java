package com.jhc.csbot.script_interpreter.core.lexer;

import com.jhc.csbot.script_interpreter.common.domain.model.lexical.ChInfo;
import com.jhc.csbot.script_interpreter.core.lexer.modules.LexerPreHandler;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: 脚本词法分析器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ScriptLexer {
    private String scriptPath; // 脚本文件路径

    @PostConstruct
    public void init() {
        // 1.先对脚本进行预处理
        List<ChInfo> chInfoList = LexerPreHandler.exec(scriptPath);
        chInfoList.forEach(chInfo -> System.out.print(chInfo.getCh()));
    }
}
