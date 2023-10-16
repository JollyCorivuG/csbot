package com.jhc.csbot.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Description: 消息发送事件
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Getter
public class MsgSendEvent extends ApplicationEvent {
    private final Long msgId;

    public MsgSendEvent(Object source, Long msgId) {
        super(source);
        this.msgId = msgId;
    }
}
