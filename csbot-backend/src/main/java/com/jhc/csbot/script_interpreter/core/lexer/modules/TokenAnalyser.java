package com.jhc.csbot.script_interpreter.core.lexer.modules;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.LexicalErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.DelimiterEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.OperatorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.lexical.ChInfo;
import com.jhc.csbot.script_interpreter.common.domain.model.lexical.LexicalToken;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;
import com.jhc.csbot.script_interpreter.utils.LexicalUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 脚本词法分析器的词法分析
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/14
 */
public class TokenAnalyser {

    private static LexicalToken newToken(TokenTypeEnum tokenType, String value, Integer line) {
        return LexicalToken.builder()
                .type(tokenType)
                .value(value)
                .line(line)
                .build();
    }

    /**
     * 判断下一个字符是否需要停止
     * @param ch
     * @return
     */
    private static boolean isNxtChStop(char ch) {
        return ch == ' ' ||
                ch == '\n' ||
                ch == '\r' ||
                DelimiterEnum.of(String.valueOf(ch)) != null ||
                OperatorEnum.of(String.valueOf(ch)) != null ||
                ch == '"';
    }

    /**
     * 词法分析, 得到 token 流
     * @param chInfoList
     * @return
     */
    private static List<LexicalToken> getTokenStream(List<ChInfo> chInfoList) {
        List<LexicalToken> tokenStream = new ArrayList<>();
        int index = 0;
        while (index < chInfoList.size()) {
            // 如果碰到空格, 换行符就直接跳过
            if (chInfoList.get(index).getCh() == ' ' || chInfoList.get(index).getCh() == '\n' || chInfoList.get(index).getCh() == '\r') {
                index++;
                continue;
            }

            // 如果碰到分隔符, 就直接加入到 token 流中
            DelimiterEnum delimiter = DelimiterEnum.of(String.valueOf(chInfoList.get(index).getCh()));
            if (delimiter != null) {
                tokenStream.add(newToken(TokenTypeEnum.DELIMITER, delimiter.getDelimiter(), chInfoList.get(index).getLine()));
                index++;
                continue;
            }

            // 如果碰到运算符, 救治杰克加入到 token 流中
            OperatorEnum operator = OperatorEnum.of(String.valueOf(chInfoList.get(index).getCh()));
            if (operator != null) {
                tokenStream.add(newToken(TokenTypeEnum.OPERATOR, operator.getOperator(), chInfoList.get(index).getLine()));
                index++;
                continue;
            }

            // 如果是字符串的开头, 就一直往后读, 读到字符串的结尾
            if (chInfoList.get(index).getCh() == '"') {
                index++;
                StringBuilder stringBuilder = new StringBuilder();
                // 一直去读, 直到碰到字符串的结尾, 如果提前碰到了换行符, 说明字符串没有闭合, 报错
                while (index < chInfoList.size() && chInfoList.get(index).getCh() != '"') {
                    if (chInfoList.get(index).getCh() == '\n') {
                        ErrorUtils.declareLexicalError(LexicalErrorEnum.UNCLOSED_STRING, chInfoList.get(index).getLine(), "字符串没有闭合");
                    }
                    stringBuilder.append(chInfoList.get(index).getCh());
                    index++;
                }
                if (index == chInfoList.size()) {
                    ErrorUtils.declareLexicalError(LexicalErrorEnum.UNCLOSED_STRING, chInfoList.get(index - 1).getLine(), "字符串没有闭合");
                } else {
                    tokenStream.add(newToken(TokenTypeEnum.STRING, stringBuilder.toString(), chInfoList.get(index).getLine()));
                    index++;
                    continue;
                }
            }

            // 否则, 一直读到下一个需要停止的字符
            StringBuilder stringBuilder = new StringBuilder();
            while (index < chInfoList.size() && !isNxtChStop(chInfoList.get(index).getCh())) {
                stringBuilder.append(chInfoList.get(index).getCh());
                index++;
            }
            TokenTypeEnum tokenType = LexicalUtils.getTokenType(stringBuilder.toString(), chInfoList.get(index - 1).getLine());
            if (tokenType != null) {
                tokenStream.add(newToken(tokenType, stringBuilder.toString(), chInfoList.get(index - 1).getLine()));
            }
        }
        return tokenStream;
    }


    public static List<LexicalToken> exec(List<ChInfo> chInfoList) {
        return getTokenStream(chInfoList);
    }
}
