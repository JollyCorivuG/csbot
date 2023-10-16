package com.jhc.csbot.service.impl;

import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.common.exception.ThrowUtils;
import com.jhc.csbot.dao.msg.MsgDao;
import com.jhc.csbot.event.MsgSendEvent;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.service.IMsgService;
import com.jhc.csbot.service.adapter.MsgAdapter;
import com.jhc.csbot.service.strategy.msg.AbstractMsgHandler;
import com.jhc.csbot.service.strategy.msg.MsgHandlerFactory;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息服务接口实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Service
public class MsgServiceImpl implements IMsgService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private MsgDao msgDao;
    @Resource
    private MsgAdapter msgAdapter;

    @Override
    public MsgInfo sendMsg(Long userId, SendMsgReq sendMsg) {
        // 1.先判断是否是合法的消息
        AbstractMsgHandler msgHandler = MsgHandlerFactory.getStrategy(sendMsg.getType());
        msgHandler.checkMsg(userId, sendMsg);

        // 2.构建消息实体并保存到数据库
        Msg msg = msgAdapter.buildMsgSave(userId, sendMsg);
        msgDao.saveMsg(msg);

        // 3.发布消息发送事件
        applicationEventPublisher.publishEvent(new MsgSendEvent(this, msg.getId()));

        // 4.返回消息信息
        return msgAdapter.buildMsgResp(msg);
    }

    @Override
    public Msg getMsgById(Long msgId) {
        Msg msg = msgDao.getMsgById(msgId);
        ThrowUtils.throwIf(msg == null, ErrorStatus.NOT_FOUND_ERROR);
        return msg;
    }
}
