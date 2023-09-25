package com.jhc.csbot.controller;

import com.jhc.csbot.common.domain.vo.resp.BasicResponse;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.service.IMsgService;
import com.jhc.csbot.utils.RequestHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 消息控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@RestController
@RequestMapping("/capi/msg")
@Tag(name = "消息相关接口")
public class MsgController {
    @Resource
    private IMsgService msgService;


    @PostMapping("/send")
    @Operation(summary = "发送信息")
    public BasicResponse<MsgInfo> sendMsg(@RequestBody SendMsgReq sendMsg) {
        Long userId = RequestHolder.get().getUid();
        return BasicResponse.success(msgService.sendMsg(userId, sendMsg));
    }
}
