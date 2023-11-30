package com.jhc.csbot.script_interpreter.test.stub;

import cn.hutool.json.JSONUtil;
import com.jhc.csbot.model.dto.msg.SendMsgReq;
import com.jhc.csbot.model.dto.msg.TextMsg;
import com.jhc.csbot.model.enums.msg.MsgTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description: Http 请求模拟器 (作为测试桩, 用于模拟 Http 请求)
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/11/24
 */
@Component
public class HttpReqMock {
    @Resource
    private WebApplicationContext webApplicationContext;

    /**
     * 构建发送消息请求
     * @return
     */
    private SendMsgReq buildSendMsgReq(String content) {
        TextMsg body = TextMsg.builder()
                .content(content)
                .build();
        return SendMsgReq.builder()
                .roomId(StubConstants.DEFAULT_USER_ID)
                .type(MsgTypeEnum.TEXT.getType())
                .body(body)
                .build();
    }

    /**
     * 模拟发送消息
     * @param content
     */
    public void sendMsg(String content) {
        // 1.构建发送消息请求
        SendMsgReq sendMsgReq = buildSendMsgReq(content);

        // 2.执行请求
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(StubConstants.SEND_MSG_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONUtil.toJsonStr(sendMsgReq)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
