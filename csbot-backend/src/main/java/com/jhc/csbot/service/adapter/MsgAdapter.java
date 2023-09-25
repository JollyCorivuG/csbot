package com.jhc.csbot.service.adapter;

import com.jhc.csbot.dao.user.UserDao;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.entity.User;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息适配器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Component
public class MsgAdapter {
    @Resource
    private UserDao userDao;

    /**
     * 构建消息实体
     * @param userId
     * @param sendMsg
     * @return
     */
    public Msg buildMsgSave(Long userId, SendMsgReq sendMsg) {
        return Msg.builder()
                .roomId(sendMsg.getRoomId())
                .fromUid(userId)
                .msgType(sendMsg.getType())
                .msgBody(sendMsg.getBody())
                .build();
    }

    /**
     * 构建消息返回体
     * @param msg
     * @return
     */
    public MsgInfo buildMsgResp(Msg msg) {
        User user = userDao.getUserById(msg.getFromUid());
        return MsgInfo.builder()
                .id(msg.getId())
                .senderInfo(UserAdapter.buildUserInfoResp(user))
                .type(msg.getMsgType())
                .body(msg.getMsgBody())
                .sendTime(msg.getCreateTime())
                .build();
    }
}