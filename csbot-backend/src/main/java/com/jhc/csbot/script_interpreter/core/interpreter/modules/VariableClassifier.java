package com.jhc.csbot.script_interpreter.core.interpreter.modules;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 变量分类器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@Slf4j
public class VariableClassifier {
    private static void filterStatusVariable() {
        // 遍历变量表, 将所有 status 类型的变量筛选出来
        List<Status> statusList = new ArrayList<>();
        VariableTable.variableMap.forEach((k, v) -> {
            if (v.getType() == KeywordEnum.STATUS) {
                statusList.add((Status) v);
            }
        });
        // 然后保证 status 变量必须存在, 且存在一个 init 为 true 的 status 变量
        if (statusList.isEmpty()) {
            log.error("必须存在一个 status 变量");
            throw new RuntimeException("必须存在一个 status 变量");
        }
        int initCount = 0;
        for (Status status : statusList) {
            if (status.getInit()) {
                initCount++;
                VariableTable.initStatus = status;
            }
        }
        if (initCount != 1) {
            log.error("必须存在一个 init 为 true 的 status 变量");
            throw new RuntimeException("必须存在一个 init 为 true 的初始 status 变量");
        }
    }

    private static void filterIntentVariable() {
        // 将所有意图变量筛选出来, 加入 VariableTable.intents
        VariableTable.variableMap.forEach((k, v) -> {
            if (v.getType() == KeywordEnum.INTENT) {
                VariableTable.intents.add((com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Intent) v);
            }
        });
        // 然后按照优先级排序, 高优先级的意图在前
        VariableTable.intents.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
    }

    public static void exec() {
        // 1.将所有 status 类型的变量筛选出来
        filterStatusVariable();

        // 2.将所有意图变量筛选出来
        filterIntentVariable();
    }
}
