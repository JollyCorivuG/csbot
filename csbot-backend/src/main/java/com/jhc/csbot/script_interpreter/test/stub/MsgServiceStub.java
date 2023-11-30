package com.jhc.csbot.script_interpreter.test.stub;

import cn.hutool.core.bean.BeanUtil;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.dto.msg.TextMsg;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.script_interpreter.core.interpreter.modules.CsBotActuator;
import com.jhc.csbot.service.IMsgService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息服务测试桩
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
@Service
public class MsgServiceStub implements IMsgService {
    @Resource
    private MysqlCrudStub mysqlCrudStub;

    @Override
    public MsgInfo sendMsg(Long userId, SendMsgReq sendMsg) {
//        // 1.先判断是否是合法的消息
//        AbstractMsgHandler msgHandler = MsgHandlerFactory.getStrategy(sendMsg.getType());
//        msgHandler.checkMsg(userId, sendMsg);

//        // 2.构建消息实体并保存到数据库
//        Msg msg = msgAdapter.buildMsgSave(userId, sendMsg);
//        msgDao.saveMsg(msg);

        // 2.构建消息实体并保存到数据库
        Msg msg = mysqlCrudStub.saveMsg(userId, sendMsg);

//        // 3.发布消息发送事件
//        if (!Objects.equals(userId, SysConstants.CS_BOT_ID)) {
//            applicationEventPublisher.publishEvent(new MsgSendEvent(this, msg.getId()));
//        }
        // 由于这里是测试桩, 所以不会发布消息发送事件, 直接执行客服机器人的逻辑
        TextMsg textMsg = BeanUtil.toBean(msg.getMsgBody(), TextMsg.class);
        CsBotActuator.acceptInputMsg(msg.getFromUid(), textMsg.getContent());

        // 4.返回消息信息
        return BeanUtil.toBean(msg, MsgInfo.class);
    }

    @Override
    public Msg getMsgById(Long msgId) {
        // 测试桩不会用到这个函数, 所以直接返回 null
        return null;
    }
}
