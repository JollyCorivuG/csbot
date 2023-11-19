package com.jhc.csbot.service.strategy.msg;

import cn.hutool.core.bean.BeanUtil;
import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.common.exception.ThrowUtils;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.dto.msg.TextMsg;
import com.jhc.csbot.model.entity.Msg;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import com.jhc.csbot.script_interpreter.core.interpreter.modules.CsBotActuator;
import com.jhc.csbot.service.IMsgService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Description: 文本消息处理器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Component
public class TextMsgHandler extends AbstractMsgHandler {
    @Resource
    private IMsgService msgService;

    /**
     * 获取消息类型
     * @return
     */
    @Override
    MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.TEXT;
    }

    /**
     * 文本消息校验
     * @param userId
     * @param sendMsg
     */
    @Override
    public void checkMsg(Long userId, SendMsgReq sendMsg) {
        TextMsg textMsg = BeanUtil.toBean(sendMsg.getBody(), TextMsg.class);
        String content = textMsg.getContent();
        ThrowUtils.throwIf(content == null, ErrorStatus.PARAMS_ERROR, "消息格式错误!");
        ThrowUtils.throwIf(content.isEmpty(), ErrorStatus.PARAMS_ERROR, "发送的信息不能为空!");
        ThrowUtils.throwIf(content.length() > 100, ErrorStatus.PARAMS_ERROR, "发送的信息过长!");
    }

    @Override
    public void replyMsg(Msg msg) {
        TextMsg textMsg = BeanUtil.toBean(msg.getMsgBody(), TextMsg.class);
        // 将消息交给客服机器人处理
        CsBotActuator.acceptInputMsg(msg.getFromUid(), textMsg.getContent());
    }
}
