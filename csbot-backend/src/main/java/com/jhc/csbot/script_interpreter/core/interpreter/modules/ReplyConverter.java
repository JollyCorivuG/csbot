package com.jhc.csbot.script_interpreter.core.interpreter.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 回复转换器, 将模板字符串转换为真正的回复
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/18
 */
public class ReplyConverter {

    /**
     * 将模板字符串转换为真正的回复
     * @param template
     * @return
     */
    public static String templateString(Long userId, String template) {
        // 模板字符串类似于 你的账单是${账单}元, ${user.nickName}, 我需要识别其中的 ${账单} 和 ${user.nickName}
        String pattern = "\\$\\{([^}]+)\\}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(template);
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            results.add(VariableResolver.exec(userId, placeholder));
        }
        for (String result : results) {
            template = template.replaceFirst(pattern, result);
        }
        return template;
    }
}
