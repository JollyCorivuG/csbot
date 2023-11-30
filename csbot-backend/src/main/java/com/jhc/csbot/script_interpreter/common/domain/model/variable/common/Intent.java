package com.jhc.csbot.script_interpreter.common.domain.model.variable.common;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import lombok.*;

import java.util.List;

/**
 * @Description: 意图变量
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Intent extends AbstractVariable {
    @Override
    public KeywordEnum getType() {
        return KeywordEnum.INTENT;
    }

    private List<String> pattern; // 正则匹配规则
    private List<String> include; // 简单包含规则
    private Integer priority; // 优先级
    private Action execAction; // 执行动作

    @Override
    public String toString() {
        String execActionName = execAction == null ? "null" : execAction.getName();
        return "Intent{" +
                "pattern=" + pattern +
                ", include=" + include +
                ", priority=" + priority +
                ", execAction=" + execActionName +
                '}';
    }
}
