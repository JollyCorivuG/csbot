package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Intent;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description: 状态变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class StatusMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.STATUS;
    }

    /**
     * status 类型的变量 maker, 注意: option 属性在所有变量 make 好之后再处理
     *
     * @param node
     */
    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        Boolean init = null;
        String speak = null;
        for (SyntaxTreeNode child : node.getChildren().values()) {
            if (child.getToken().getValue().equals("init")) {
                init = Boolean.parseBoolean(child.getChildren().values().iterator().next().getToken().getValue());
            }
            if (child.getToken().getValue().equals("speak")) {
                speak = child.getChildren().values().iterator().next().getToken().getValue();
            }
        }
        Status v = new Status();
        v.setName(variableName);
        v.setInit(init);
        v.setSpeak(speak);
        VariableTable.variableMap.put(variableName, v);
    }


    /**
     * 处理 status 变量中的 option 属性
     *
     * @param node
     */
    public void handleOption(SyntaxTreeNode node) {
        String statusName = node.getToken().getValue();
        Status status = (Status) VariableTable.getByName(statusName);
        assert status != null;
        for (SyntaxTreeNode child : node.getChildren().values()) {
            if (child.getToken().getValue().equals("init") || child.getToken().getValue().equals("speak")) {
                continue;
            }
            // 只需要处理 option 属性
            for (SyntaxTreeNode optionChild : child.getChildren().values()) {
                if (!Objects.equals(optionChild.getToken().getValue(), "[") && !Objects.equals(optionChild.getToken().getValue(), "]")) {
                    // 遍历每个虚节点, 找到每一个 option
                    String input = null;
                    String text = null;
                    Intent purpose = null;
                    for (SyntaxTreeNode childChild : optionChild.getChildren().values()) {
                        if (childChild.getToken().getValue().equals("input")) {
                            input = childChild.getChildren().values().iterator().next().getToken().getValue();
                        }
                        if (childChild.getToken().getValue().equals("text")) {
                            text = childChild.getChildren().values().iterator().next().getToken().getValue();
                        }
                        if (childChild.getToken().getValue().equals("purpose")) {
                            purpose = (Intent) VariableTable.getByName(childChild.getChildren().values().iterator().next().getToken().getValue());
                            assert purpose != null;
                        }
                    }
                    Status.Option option = new Status.Option();
                    option.setInput(input);
                    option.setText(text);
                    option.setPurpose(purpose);
                    status.getOptions().add(option);
                }
            }

        }
    }
}
