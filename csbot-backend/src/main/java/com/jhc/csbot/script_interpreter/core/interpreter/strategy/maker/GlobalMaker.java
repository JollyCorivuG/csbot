package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Global;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description: 全局变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class GlobalMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.GLOBAL;
    }

    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        for (SyntaxTreeNode child: node.getChildren().values()) {
            if (Objects.equals(child.getToken().getValue(), "value")) {
                Global v = new Global();
                v.setName(variableName);
                v.setValue(child.getChildren().values().iterator().next().getToken().getValue());
                VariableTable.variableMap.put(variableName, v);
            }
        }
    }
}
