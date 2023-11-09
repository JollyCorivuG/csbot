package com.jhc.csbot.script_interpreter.core.parser;

import com.jhc.csbot.script_interpreter.common.domain.enums.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description: 分析器的预处理
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
@Slf4j
public class ParserPreHandler {
    public static String exec(String scriptPath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(scriptPath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            String script = sb.toString();
            script = script.replaceAll("[\\n\\r\\t]", " ").trim();
            while (script.contains("  ")) {
                script = script.replaceAll("  ", " ");
            }
            return script;
        } catch (IOException e) {
            log.error("脚本文件读取错误", e);
            throw new ScriptException(ScriptErrorEnum.FILE_NOT_FOUND);
        } finally {
            try {
                // 关闭文件流
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("脚本文件关闭错误", e);
            }
        }
    }
}
