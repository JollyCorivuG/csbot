package com.jhc.csbot.service;

import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.vo.msg.MsgInfo;

/**
 * @Description: 消息服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
public interface IMsgService {
    /**
     * 发送消息
     * @param userId
     * @param sendMsg
     * @return
     */
    MsgInfo sendMsg(Long userId, SendMsgReq sendMsg);


    /**
     * 根据 id 获取消息
     * @param msgId
     * @return
     */
    Msg getMsgById(Long msgId);
}
