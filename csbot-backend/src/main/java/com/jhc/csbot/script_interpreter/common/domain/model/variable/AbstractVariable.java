package com.jhc.csbot.script_interpreter.common.domain.model.variable;

import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 变量抽象类, 也就是所有类型变量的父类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/17
 */
@Getter
@Setter
public abstract class AbstractVariable {
    public String name;

    /**
     * 获取变量类型
     * @return
     */
    public abstract KeywordEnum getType();

    public void setName(String name) {
        this.name = name;
    }
}
