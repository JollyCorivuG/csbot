package com.jhc.csbot.script_interpreter.core.interpreter.environment;

import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;
import com.jhc.csbot.script_interpreter.core.interpreter.modules.CsBotActuator;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 记录所有用户的状态
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/18
 */
public class UsersState {
    private static final ConcurrentHashMap<Long, Status> stateMap = new ConcurrentHashMap<>();

    /**
     * 当用户首次进行 websocket 连接时, 初始化用户的状态
     * @param userId
     */
    public static void init(Long userId) {
        stateMap.put(userId, VariableTable.initStatus);
        CsBotActuator.execStatus(userId, VariableTable.initStatus);
    }

    /**
     * 当用户进行 websocket 断开时, 删除用户的状态
     * @param userId
     */
    public static void exit(Long userId) {
        stateMap.remove(userId);
    }

    /**
     * 改变用户的状态
     * @param userId
     * @param status
     */
    public static void change(Long userId, Status status) {
        Status oldStatus = stateMap.get(userId);
        stateMap.put(userId, status);
        if (!Objects.equals(oldStatus.getName(), status.getName())) {
            CsBotActuator.execStatus(userId, status);
        }
    }

    /**
     * 获取用户的状态
     * @param userId
     * @return
     */
    public static Status get(Long userId) {
        Status status = stateMap.get(userId);
        if (status == null) {
            throw new RuntimeException("用户" + userId + "不存在");
        }
        return status;
    }

    /**
     * 清空所有用户的状态
     */
    public static void clear() {
        stateMap.clear();
    }
}
