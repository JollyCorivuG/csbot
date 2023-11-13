package com.jhc.csbot.script_interpreter.core.lexer.modules;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.lexical.ChInfo;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 脚本词法分析器的预处理
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/13
 */
public class LexerPreHandler {
    /**
     * 读取文件得到流
     * @param scriptPath
     * @return
     */
    private static List<ChInfo> getStream(String scriptPath) {
        BufferedReader reader = null;
        List<ChInfo> stream = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(scriptPath));
            int line = 1, ch;
            while ((ch = reader.read()) != -1) {
                // 如果是 tab 符号，替换为 4 个空格
                if (ch == '\t') {
                    // 加入四个空格到流中
                    for (int i = 0; i < 4; i++) {
                        stream.add(ChInfo.builder().ch(' ').line(line).build());
                    }
                } else {
                    stream.add(ChInfo.builder().ch((char) ch).line(line).build());
                }
                if (ch == '\n') {
                    line++;
                }
            }
        } catch (IOException e) {
            ErrorUtils.declareScriptError(ScriptErrorEnum.FILE_NOT_FOUND);
        } finally {
            try {
                // 关闭文件流
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                ErrorUtils.declareScriptError(ScriptErrorEnum.FILE_CLOSE_ERROR);
            }
        }
        return stream;
    }

    /**
     * 删除注释
     * @param stream
     * @return
     */
    private static List<ChInfo> delComment(List<ChInfo> stream) {
        List<ChInfo> newStream = new ArrayList<>();
        List<ChInfo> multiLineCommentContent = new ArrayList<>();
        boolean isReadingString = false; // 是否正在读取字符串
        for (int i = 0; i < stream.size(); i++) {
            ChInfo chInfo = stream.get(i);
            if (chInfo.getCh() == '"') {
                isReadingString = !isReadingString;
            }
            if (isReadingString) {
                newStream.add(chInfo);
                if (chInfo.getCh() == '\n') {
                    isReadingString = false;
                }
            } else {
                // 如果是单行注释，跳过该行
                if (chInfo.getCh() == '/' && i + 1 < stream.size() && stream.get(i + 1).getCh() == '/') {
                    while (i < stream.size() && stream.get(i).getCh() != '\n') {
                        i++;
                    }
                    if (i < stream.size()) {
                        newStream.add(stream.get(i));
                    }
                    continue;
                }
                // 如果是多行注释
                if (chInfo.getCh() == '/' && i + 1 < stream.size() && stream.get(i + 1).getCh() == '*') {
                    multiLineCommentContent.add(chInfo);
                    i++;
                    multiLineCommentContent.add(stream.get(i));
                    i++;
                    while (i < stream.size() && !(stream.get(i).getCh() == '*' && i + 1 < stream.size() && stream.get(i + 1).getCh() == '/')) {
                        multiLineCommentContent.add(stream.get(i));
                        i++;
                    }
                    if (i < stream.size()) {
                        multiLineCommentContent.clear();
                        i++;
                    } else {
                        // 将多行注释中的内容加入到流中
                        newStream.addAll(multiLineCommentContent);
                        return newStream;
                    }
                    continue;
                }
                newStream.add(chInfo);
            }
        }
        return newStream;
    }

    public static List<ChInfo> exec(String scriptPath) {
        // 1.读取文件得到流
        List<ChInfo> stream = getStream(scriptPath);

        // 2.删除注释
        return delComment(stream);
    }
}
