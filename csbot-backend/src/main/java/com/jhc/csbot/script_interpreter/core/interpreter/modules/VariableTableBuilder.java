package com.jhc.csbot.script_interpreter.core.interpreter.modules;

import com.jhc.csbot.script_interpreter.common.domain.model.syntax.SyntaxTreeNode;
import com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker.AbstractVariableMaker;
import com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker.StatusMaker;
import com.jhc.csbot.script_interpreter.core.interpreter.strategy.maker.VariableMakerFactory;

import java.util.List;

/**
 * @Description: 变量表构造器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
public class VariableTableBuilder {

    /**
     * 变量遍历的顺序
     */
    private static final List<String> makeOrder = List.of(
            "env",
            "global",
            "query",
            "status",
            "action",
            "intent"
    );

    private static void RoughlyHandle(SyntaxTreeNode root) {
        for (String order : makeOrder) {
            SyntaxTreeNode node = root.getChildren().get(order);
            if (node == null) {
                continue;
            }
            AbstractVariableMaker maker = VariableMakerFactory.getStrategy(order);
            assert maker != null;
            for (SyntaxTreeNode child : node.getChildren().values()) {
                maker.make(child);
            }
        }
    }

    private static void handleStatusOption(SyntaxTreeNode root) {
        StatusMaker statusMaker = (StatusMaker) VariableMakerFactory.getStrategy("status");
        assert statusMaker != null;
        SyntaxTreeNode statusNode = root.getChildren().get("status");
        if (statusNode == null) {
            return;
        }
        for (SyntaxTreeNode child : statusNode.getChildren().values()) {
            statusMaker.handleOption(child);
        }
    }


    public static void exec(SyntaxTreeNode root) {
        // 1.先经过大致地处理得到变量表
        RoughlyHandle(root);

        // 2.然后处理 status 变量中的 option 属性
        handleStatusOption(root);
    }
}
