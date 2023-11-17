package com.jhc.csbot.script_interpreter.core.parser;

import com.jhc.csbot.script_interpreter.common.domain.model.lexical.LexicalToken;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeInfo;
import com.jhc.csbot.script_interpreter.core.parser.modules.SyntaxChecker;
import com.jhc.csbot.script_interpreter.core.parser.modules.SyntaxTreeBuilder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: 脚本解析器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
public class ScriptParser {
    private List<LexicalToken> tokenStream; // token stream 流
    private SyntaxTreeInfo syntaxTree;

    public ScriptParser(List<LexicalToken> tokenStream) {
        this.tokenStream = tokenStream;
    }

    @PostConstruct
    public void init() {
        // 1.根据 token 流构建语法树
        this.syntaxTree = SyntaxTreeBuilder.exec(tokenStream);
        log.info("语法树构建完成");

        // 2.对语法树进行语法检查
        SyntaxChecker.exec(this.syntaxTree);
        log.info("语法检查完成");
    }
}
