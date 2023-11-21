package com.jhc.csbot.script_interpreter.core.interpreter.modules;

import cn.hutool.extra.spring.SpringUtil;
import com.jhc.csbot.common.constants.SysConstants;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.dto.msg.TextMsg;
import com.jhc.csbot.model.dto.ws_msg.WSMsgResp;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import com.jhc.csbot.model.enums.ws_msg.WSMsgRespTypeEnum;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Action;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Intent;
import com.jhc.csbot.script_interpreter.common.domain.model.variable.common.Status;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.UsersState;
import com.jhc.csbot.script_interpreter.core.interpreter.environment.VariableTable;
import com.jhc.csbot.service.IMsgService;
import com.jhc.csbot.service.IWebSocketService;

import java.util.List;

/**
 * @Description: 机器人执行器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/18
 */
public class CsBotActuator {
    private static final IWebSocketService webSocketService = SpringUtil.getBean(IWebSocketService.class); // ws 服务, 用于主动推送消息
    private static final IMsgService msgService = SpringUtil.getBean(IMsgService.class); // 消息服务, 用于发送消息


    /**
     * 执行消息发送
     * @param rawReply
     * @param roomId
     */
    private static void execMsgSend(String rawReply, Long roomId) {
        String reply = ReplyConverter.templateString(roomId, rawReply);
        SendMsgReq req = SendMsgReq.builder()
                .type(MsgTypeEnum.TEXT.getType())
                .body(TextMsg.builder().content(reply).build())
                .roomId(roomId)
                .build();
        MsgInfo msg = msgService.sendMsg(SysConstants.CS_BOT_ID, req);
        webSocketService.sendToAssignedRoom(
                roomId,
                WSMsgResp.builder().type(WSMsgRespTypeEnum.MESSAGE.getType()).data(msg).build(),
                msg.getSenderInfo().getId()
        );
    }

    /**
     * 当用户进入某个状态时, 执行状态的相关信息
     * @param userId
     * @param status
     */
    public static void execStatus(Long userId, Status status) {
        // 1.执行状态的 speak
        String speak = status.getSpeak();
        if (status.getSpeak() != null) {
            execMsgSend(speak, userId);
        }

        // 2.执行状态的 options
        if (!status.getOptions().isEmpty()) {
            StringBuilder showMsg = new StringBuilder("您可以通过输入以下选项进行: \n");
            for (Status.Option option : status.getOptions()) {
                showMsg.append(option.getInput()).append(": ").append(option.getText()).append("\n");
            }
            // 去掉最后面的换行符
            showMsg.deleteCharAt(showMsg.length() - 1);
            execMsgSend(showMsg.toString(), userId);
        }
    }

    /**
     * 当识别用户意图后, 执行意图对应的动作
     * @param userId
     * @param action
     */
    public static void execAction(Long userId, Action action) {
        // 1.执行 action 的 reply
        String reply = action.getReply();
        if (reply != null) {
            execMsgSend(reply, userId);
        }

        // 2.更新用户的状态
        Status status = action.getAfterStatus();
        if (status != null) {
            UsersState.change(userId, status);
        }
    }


    /**
     * 匹配用户输入的消息, 返回匹配到的意图
     * @param userId
     * @param msg
     * @return
     */
    public static Intent matchIntent(Long userId, String msg) {
        // 遍历变量表中的意图 (已按照优先级排序)
        for (Intent intent : VariableTable.intents) {
            // 匹配正则表达式
            List<String> patterns = intent.getPattern();
            for (String pattern : patterns) {
                if (msg.matches(pattern)) {
                    return intent;
                }
            }
            // 匹配 include
            List<String> includes = intent.getInclude();
            for (String include : includes) {
                if (msg.contains(include)) {
                    return intent;
                }
            }
        }
        return null;
    }

    /**
     * 接收用户输入的消息
     * @param userId
     * @param msg
     */
    public static void acceptInputMsg(Long userId, String msg) {
        // 1.如果状态中有 option 选项, 就优先匹配 option
        // 1.1获取当前状态
        Status status = UsersState.get(userId);
        // 1.2如果当前状态中有 option 选项, 就优先匹配 option
        if (!status.getOptions().isEmpty()) {
            for (Status.Option option : status.getOptions()) {
                if (option.getInput().equals(msg)) {
                    // 1.2.1如果匹配到了, 就执行 option 的动作
                    execAction(userId, option.getPurpose().getExecAction());
                    return;
                }
            }
        }

        // 2.如果没有匹配上 option, 就根据输入匹配意图
        Intent intent = matchIntent(userId, msg);
        if (intent == null) {
            // 如果没有匹配上, 则执行一条默认的 reply
            if (VariableTable.defaultAction != null) {
                execAction(userId, VariableTable.defaultAction);
            }
            return;
        }
        // 如果匹配上了, 就执行意图的动作
        execAction(userId, intent.getExecAction());
    }
}
