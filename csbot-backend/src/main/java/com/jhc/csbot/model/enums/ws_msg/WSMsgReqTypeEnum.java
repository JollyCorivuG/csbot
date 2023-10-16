package com.jhc.csbot.model.enums.ws_msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: websocket 消息类型枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@AllArgsConstructor
@Getter
public enum WSMsgReqTypeEnum {
    HEARTBEAT(0, "心跳包"),
    AUTHORIZE(1, "登录认证"),
    ;

    private final Integer type;
    private final String description;

    private static final Map<Integer, WSMsgReqTypeEnum> cache;

    static {
        cache = Arrays.stream(WSMsgReqTypeEnum.values()).collect(Collectors.toMap(WSMsgReqTypeEnum::getType, Function.identity()));
    }


    public static WSMsgReqTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
