package com.jhc.csbot.service;

import com.jhc.csbot.dao.msg.MsgDao;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.dto.msg.TextMsg;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import com.jhc.csbot.service.adapter.MsgAdapter;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 消息服务测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@SpringBootTest
public class MsgServiceTest {
    @Resource
    private MsgDao msgDao;
    @Resource
    private MsgAdapter msgAdapter;

    @Test
    void testInsertMsg() {
        TextMsg body = TextMsg.builder()
                .content("你好呀~")
                .build();
        SendMsgReq req = new SendMsgReq();
        req.setType(MsgTypeEnum.TEXT.getType());
        req.setRoomId(1L);
        req.setBody(body);
        Msg msg = msgAdapter.buildMsgSave(1L, req);
        msgDao.saveMsg(msg);
        System.out.println(msg);
    }
}
