package com.jhc.csbot.script_interpreter.core.interpreter.modules;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.jhc.csbot.dao.user.UserDao;
import com.jhc.csbot.model.entity.User;
import com.jhc.csbot.script_interpreter.common.domain.enums.lexical.KeywordEnum;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.AbstractVariable;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Global;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive.Env;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.exclusive.Query;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;

import java.util.Map;

/**
 * @Description: 变量解析器, 将变量解析为真正的值
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/18
 */
public class VariableResolver {
    private static final UserDao userDao = SpringUtil.getBean(UserDao.class);


    /**
     * 解析 env 变量, 目前只支持捕获 user 类
     * @param userId
     * @param v
     * @param key
     * @return
     */
    private static String resolveEnv(Long userId, Env v, String key) {
        if (v.getClazz() == User.class) {
            User user = userDao.getUserById(userId);
            if (user == null) {
                throw new RuntimeException("用户" + userId + "不存在");
            }
            return JSONUtil.toBean(JSONUtil.toJsonStr(user), Map.class).get(key).toString();
        }
        return v.getName() + "." + key;
    }


    /**
     * 解析 query 变量
     * @param userId
     * @param v
     * @return
     */
    private static String resolveQuery(Long userId, Query v) {
        // 根据 v 的属性拼接 sql 语句
        StringBuilder sql = new StringBuilder("select " + v.getField() + " from " + v.getTable() + " where 1=1");
        v.getFilters().forEach((k, v1) -> {
            AbstractVariable key = v1.getKey();
            switch (key.getType()) {
                case GLOBAL -> {
                    Global g = (Global) key;
                    sql.append(" and ").append(k).append(" = '").append(g.getValue()).append("'");
                }
                case ENV -> {
                    Env e = (Env) key;
                    sql.append(" and ").append(k).append(" = '").append(resolveEnv(userId, e, v1.getValue())).append("'");
                }
                default -> {
                    throw new RuntimeException("query 变量的过滤条件只能是 global 或 env");
                }
            }
        });
        // 利用 mybatis-plus 的 SqlRunner 执行 sql 语句
        Map<String, Object> maps = SqlRunner.db().selectOne(sql.toString());
        return maps.get(v.getField()).toString();
    }

    /**
     * 将变量解析为真正的值
     * @param var
     * @return
     */
    public static String exec(Long userId, String var) {
        // 如果 var 当中不含有一个 . 运算符
        if (!var.contains(".") && VariableTable.getByName(var) == null) {
            return var;
        }
        if (!var.contains(".")) {
            AbstractVariable v = VariableTable.getByName(var);
            switch (v.getType()) {
                case GLOBAL -> {
                    Global g = (Global) v;
                    return g.getValue();
                }
                case QUERY -> {
                    Query q = (Query) v;
                    return resolveQuery(userId, q);
                }
                default -> {
                    return var;
                }
            }
        }
        // 按照 . 分成两部分
        String[] split = var.split("\\.");
        if (split.length != 2) {
            return var;
        }
        AbstractVariable v = VariableTable.getByName(split[0]);
        if (v.getType() != KeywordEnum.ENV) {
            return var;
        }
        Env e = (Env) v;
        String key = split[1];
        return resolveEnv(userId, e, key);
    }
}
