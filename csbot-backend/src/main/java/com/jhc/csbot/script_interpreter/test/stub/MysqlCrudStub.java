package com.jhc.csbot.script_interpreter.test.stub;

import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.entity.Msg;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: Mysql 增删改查测试桩
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
@Component
public class MysqlCrudStub {
    private static final Long DEFAULT_MSG_ID = 1L; // 默认消息 id

    /**
     * 保存消息
     * @param sendMsg
     * @return
     */
    public Msg saveMsg(Long userId, SendMsgReq sendMsg) {
        return Msg.builder()
                .id(DEFAULT_MSG_ID)
                .roomId(sendMsg.getRoomId())
                .fromUid(userId)
                .msgType(sendMsg.getType())
                .msgBody(sendMsg.getBody())
                .createTime(new Date())
                .createTime(new Date())
                .build();
    }

    /**
     * 查询
     * @return
     */
    public String query() {
        return "mysql 测试桩查询结果";
    }
}
