package com.jhc.csbot.service.strategy.msg;


import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import com.jhc.csbot.model.vo.msg.MsgInfo;
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

    /**
     * 检查消息
     * @param userId
     * @param sendMsg
     */
    public abstract void checkMsg(Long userId, SendMsgReq sendMsg);

    /**
     * 回复消息
     * @param msg
     * @return
     */
    public abstract MsgInfo replyMsg(Msg msg);
}
