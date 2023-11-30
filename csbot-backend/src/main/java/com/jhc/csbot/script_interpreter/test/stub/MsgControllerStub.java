package com.jhc.csbot.script_interpreter.test.stub;

import com.jhc.csbot.common.domain.vo.resp.BasicResponse;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.vo.msg.MsgInfo;
import com.jhc.csbot.service.IMsgService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 消息控制器测试桩
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
@RestController
@RequestMapping("/test/capi/msg")
public class MsgControllerStub {
    @Resource(name = "msgServiceStub")
    private IMsgService msgServiceStub; // 指定名称为 msgServiceStub 的测试桩

    @PostMapping("/send")
    public BasicResponse<MsgInfo> sendMsg(@RequestBody SendMsgReq sendMsg) {
        Long userId = StubConstants.DEFAULT_USER_ID;
        return BasicResponse.success(msgServiceStub.sendMsg(userId, sendMsg));
    }
}

