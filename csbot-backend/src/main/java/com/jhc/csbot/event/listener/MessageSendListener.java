package com.jhc.csbot.event.listener;

import com.jhc.csbot.event.MessageSendEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Description: 消息发送事件监听器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Component
@Slf4j
public class MessageSendListener {
    /**
     * 当接受到用户发送的信息时, 需要按照脚本解释器执行消息回复
     * @param event
     */
    @Async
    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
    public void executeMsgReply(MessageSendEvent event) {
        log.info("executeMsgReply: {}", event.getMsgId());
    }
}
