package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Action;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;
import org.springframework.stereotype.Component;

/**
 * @Description: 动作变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class ActionMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.ACTION;
    }

    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        String reply = null;
        Status afterStatus = null;
        for (SyntaxTreeNode child : node.getChildren().values()) {
            if (child.getToken().getValue().equals("reply")) {
                reply = child.getChildren().values().iterator().next().getToken().getValue();
            }
            if (child.getToken().getValue().equals("after_status")) {
                AbstractVariable tmp = VariableTable.getByName(child.getChildren().values().iterator().next().getToken().getValue());
                if (tmp.getType() != KeywordEnum.STATUS) {
                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.ATTRIBUTE_TYPE_ERROR, child.getChildren().values().iterator().next().getToken().getLine(), "action 类型的变量中 after_status 属性的类型必须是 status");
                }
                afterStatus = (Status) tmp;
            }
        }
        Action v = new Action();
        v.setName(variableName);
        v.setReply(reply);
        v.setAfterStatus(afterStatus);
        VariableTable.variableMap.put(variableName, v);
    }
}
