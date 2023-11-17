package com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker;

import com.jhc.csbot.script_interpreter.common.domain.enums.error.SyntaxErrorEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.TokenTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive.Query;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import com.jhc.csbot.script_interpreter.utils.ErrorUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 查询变量构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/15
 */
@Component
public class QueryMaker extends AbstractVariableMaker {

    @Override
    KeywordEnum getVariableType() {
        return KeywordEnum.QUERY;
    }

    @Override
    public void make(SyntaxTreeNode node) {
        String variableName = node.getToken().getValue();
        String table = null;
        Map<String, Map.Entry<AbstractVariable, String>> filters = new HashMap<>();
        String field = null;
        for (SyntaxTreeNode child : node.getChildren().values()) {
            if (child.getToken().getValue().equals("table")) {
                table = child.getChildren().values().iterator().next().getToken().getValue();
            }
            if (child.getToken().getValue().equals("field")) {
                field = child.getChildren().values().iterator().next().getToken().getValue();
            }
            if (child.getToken().getValue().equals("filters")) {
                for (SyntaxTreeNode filter : child.getChildren().values()) {
                    String filterName = filter.getToken().getValue();
                    if (filter.getChildren().size() == 3) {
                        AbstractVariable leftPart = null;
                        String rightPart = null;
                        for (SyntaxTreeNode filterChild : filter.getChildren().values()) {
                            if (filterChild.getToken().getType() == TokenTypeEnum.OPERATOR) {
                                continue;
                            }
                            if (filterChild.getLeftPartOfOperator()) {
                                leftPart = VariableTable.getByName(filterChild.getToken().getValue());
                                if (leftPart.getType() != KeywordEnum.ENV) {
                                    ErrorUtils.declareSyntaxError(SyntaxErrorEnum.NOT_SUPPORT_USE, filterChild.getToken().getLine(), "只有 env 类型的变量才支持成员访问符 . 的用法");
                                }
                            } else {
                                rightPart = filterChild.getToken().getValue();
                            }
                        }
                        filters.put(filterName, new HashMap.SimpleEntry<>(leftPart, rightPart));
                    } else {
                        // 直接当成变量
                        filters.put(filterName, new HashMap.SimpleEntry<>(VariableTable.getByName(filter.getToken().getValue()), null));
                    }
                }
            }
        }
        Query v = new Query();
        v.setName(variableName);
        v.setTable(table);
        v.setFilters(filters);
        v.setField(field);
        VariableTable.variableMap.put(variableName, v);
    }
}
