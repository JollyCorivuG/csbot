package com.jhc.csbot.service.strategy.msg;


import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import jakarta.annotation.PostConstruct;

/**
 * @Description: 消息处理器抽象类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
public abstract class AbstractMsgHandler {
    @PostConstruct
    private void init() {
        MsgHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }

    abstract MsgTypeEnum getMsgTypeEnum();

    public abstract void checkMsg(Long userId, SendMsgReq sendMsg);
}
