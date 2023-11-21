package com.jhc.csbot.script_interpreter.core.interpreter.environment;

import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Action;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Intent;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 变量表
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
public class VariableTable {
    public static Map<String, AbstractVariable> variableMap = new HashMap<>();
    public static List<Intent> intents = new ArrayList<>();
    public static Status initStatus;
    public static Action defaultAction;

    public static AbstractVariable getByName(String name) {
        if (variableMap.get(name) == null) {
            throw new RuntimeException("变量" + name + "不存在");
        }
        return variableMap.get(name);
    }

    public static void showInfo() {
        System.out.println("变量表信息：");
        variableMap.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
    }
}
