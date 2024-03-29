package com.jhc.csbot.model.dto.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 发送消息请求
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMsgReq {
    private Long roomId;
    private Integer type;
    private Object body;
}
