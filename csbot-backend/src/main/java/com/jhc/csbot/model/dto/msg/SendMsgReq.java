package com.jhc.csbot.model.dto.msg;

import lombok.Data;

/**
 * @Description: 发送消息请求
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Data
public class SendMsgReq {
    private Long roomId;
    private Integer type;
    private Object body;
}
