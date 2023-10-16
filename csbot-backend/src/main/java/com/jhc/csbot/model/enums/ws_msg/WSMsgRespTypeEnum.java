package com.jhc.csbot.model.enums.ws_msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: websocket 消息类型枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@AllArgsConstructor
@Getter
public enum WSMsgRespTypeEnum {
    MESSAGE(0, "新消息"),
    ONLINE_OFFLINE_NOTIFY(1, "上下线通知"),
    HEAD_SHAKE_SUCCESS(2, "握手成功"),
    HEAD_SHAKE_FAIL(3, "握手失败"),
    ;

    private final Integer type;
    private final String description;
}
