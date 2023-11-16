package com.jhc.csbot.script_interpreter.core.lexer;

import com.jhc.csbot.script_interpreter.common.domain.model.lexical.ChInfo;
import com.jhc.csbot.script_interpreter.common.domain.model.lexical.LexicalToken;
import com.jhc.csbot.script_interpreter.core.lexer.modules.LexerPreHandler;
import com.jhc.csbot.script_interpreter.core.lexer.modules.TokenAnalyser;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class ScriptLexer {
    private String scriptPath; // 脚本文件路径
    private List<LexicalToken> tokenStream; // token stream 流

    public ScriptLexer(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    @PostConstruct
    public void init() {
        // 1.先对脚本进行预处理
        List<ChInfo> chInfoList = LexerPreHandler.exec(scriptPath);

        // 2.对预处理后的脚本进行词法分析
        this.tokenStream = TokenAnalyser.exec(chInfoList);
    }
}
