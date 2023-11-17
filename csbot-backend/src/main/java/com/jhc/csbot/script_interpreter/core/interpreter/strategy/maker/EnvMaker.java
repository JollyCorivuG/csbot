package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive.Env;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description: 环境变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class EnvMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.ENV;
    }

    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        String packageName = null;
        String className = null;
        for (SyntaxTreeNode child: node.getChildren().values()) {
            if (Objects.equals(child.getToken().getValue(), "package_name")) {
                packageName = child.getChildren().values().iterator().next().getToken().getValue();
            }
            if (Objects.equals(child.getToken().getValue(), "class_name")) {
                className = child.getChildren().values().iterator().next().getToken().getValue();
            }
        }
        String fullClassName = packageName + "." + className;
        try {
            Class<?> clazz = Class.forName(fullClassName);
            Env v = new Env();
            v.setName(variableName);
            v.setClazz(clazz);
            VariableTable.variableMap.put(variableName, v);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到类：" + fullClassName);
        }
    }
}
