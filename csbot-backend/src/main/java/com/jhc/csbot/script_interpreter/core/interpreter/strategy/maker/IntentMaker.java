package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Action;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Intent;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 目的变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class IntentMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.INTENT;
    }

    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        List<String> pattern = new ArrayList<>();
        List<String> include = new ArrayList<>();
        Integer priority = null;
        Action execAction = null;
        for (SyntaxTreeNode child : node.getChildren().values()) {
            if (child.getToken().getValue().equals("pattern")) {
                for (SyntaxTreeNode patternChild : child.getChildren().values()) {
                    if (!Objects.equals(patternChild.getToken().getValue(), "[") && !Objects.equals(patternChild.getToken().getValue(), "]")) {
                        pattern.add(patternChild.getToken().getValue());
                    }
                }
            }
            if (child.getToken().getValue().equals("include")) {
                for (SyntaxTreeNode includeChild : child.getChildren().values()) {
                    if (!Objects.equals(includeChild.getToken().getValue(), "[") && !Objects.equals(includeChild.getToken().getValue(), "]")) {
                        include.add(includeChild.getToken().getValue());
                    }
                }
            }
            if (child.getToken().getValue().equals("priority")) {
                priority = Integer.parseInt(child.getChildren().values().iterator().next().getToken().getValue());
            }
            if (child.getToken().getValue().equals("exec_action")) {
                execAction = (Action) VariableTable.getByName(child.getChildren().values().iterator().next().getToken().getValue());
            }
        }
        Intent v = new Intent();
        v.setName(variableName);
        v.setPattern(pattern);
        v.setInclude(include);
        v.setPriority(priority);
        v.setExecAction(execAction);
        VariableTable.variableMap.put(variableName, v);
    }
}
