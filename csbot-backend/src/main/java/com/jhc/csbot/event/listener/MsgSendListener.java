package com.jhc.csbot.event.listener;

import com.jhc.csbot.event.MsgSendEvent;
import com.jhc.csbot.model.dto.ws_msg.WSMsgResp;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.enums.ws_msg.WSMsgRespTypeEnum;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.service.IMsgService;
import com.jhc.csbot.service.IWebSocketService;
import com.jhc.csbot.service.strategy.msg.AbstractMsgHandler;
import com.jhc.csbot.service.strategy.msg.MsgHandlerFactory;
import jakarta.annotation.Resource;
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
public class MsgSendListener {
    @Resource
    private IMsgService msgService;

    @Resource
    private IWebSocketService webSocketService;


    /**
     * 当接受到用户发送的信息时, 需要按照脚本解释器执行消息回复
     * @param event
     */
    @Async
    @TransactionalEventListener(classes = MsgSendEvent.class, fallbackExecution = true)
    public void executeMsgReply(MsgSendEvent event) {
        // 1.根据消息 id 查询消息
        Msg msg = msgService.getMsgById(event.getMsgId());

        // 2.得到客服机器人回复的消息
        AbstractMsgHandler msgHandler = MsgHandlerFactory.getStrategy(msg.getMsgType());
        MsgInfo replyMsg = msgHandler.replyMsg(msg);

        // 3.将消息推送到指定的房间
        webSocketService.sendToAssignedRoom(
                msg.getRoomId(),
                WSMsgResp.builder().type(WSMsgRespTypeEnum.MESSAGE.getType()).data(replyMsg).build(),
                replyMsg.getSenderInfo().getId()
        );
    }
}
