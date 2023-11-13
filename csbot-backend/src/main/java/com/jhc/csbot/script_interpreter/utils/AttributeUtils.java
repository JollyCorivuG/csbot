package com.jhc.csbot.script_interpreter.utils;

import com.jhc.csbot.script_interpreter.common.domain.enums.AttributeTypeEnum;
import com.jhc.csbot.script_interpreter.common.domain.enums.error.ScriptErrorEnum;
import com.jhc.csbot.script_interpreter.common.exception.ScriptException;

/**
 * @Description: 属性工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/9
 */
public class AttributeUtils {
    public static AttributeTypeEnum inferType(String value) {
        // 1.判断是否是字符串, 以 " 开头，以 " 结尾
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return AttributeTypeEnum.STRING;
        }

        // 2.判断是否是整数
        try {
            Integer.parseInt(value);
            return AttributeTypeEnum.INT;
        } catch (NumberFormatException e) {
            // 不是整数
        }

        // 3.判断是否是布尔值
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return AttributeTypeEnum.BOOLEAN;
        }

        // 4.判断是否是对象
        if (value.startsWith("{") && value.endsWith("}")) {
            return AttributeTypeEnum.OBJECT;
        }

        // 5.判断是否是变量, 命名规范是, 可以含有中文, 但不能以数字开头, 不能含有特殊字符
        if (value.matches("[a-zA-Z_一-龥][a-zA-Z0-9_一-龥]*")) {
            return AttributeTypeEnum.VARIABLE;
        }

        // 6.判断是否是字符串数组或者对象数组
        if (value.startsWith("[") && value.endsWith("]")) {
            // 6.1.判断是否是字符串数组, 就是先把 []去掉，然后用 , 分割，然后每个元素都是字符串
            String[] split = value.substring(1, value.length() - 1).split(",");
            boolean isStringArray = true;
            for (String s : split) {
                if (!s.startsWith("\"") || !s.endsWith("\"")) {
                    isStringArray = false;
                    break;
                }
            }
            if (isStringArray) {
                return AttributeTypeEnum.STRING_ARRAY;
            }
            boolean isObjectArray = true;
            for (String s : split) {
                if (!s.startsWith("{") || !s.endsWith("}")) {
                    isObjectArray = false;
                    break;
                }
            }
            if (isObjectArray) {
                return AttributeTypeEnum.OBJECT_ARRAY;
            }
        }

        // 7.无法判断
        throw new ScriptException(ScriptErrorEnum.ATTRIBUTE_TYPE_NOT_FOUND);
    }
}
