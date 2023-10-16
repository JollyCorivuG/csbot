package com.jhc.csbot.model.dto.ws_msg;

import lombok.Data;

/**
 * @Description: 前端传来的 ws 消息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Data
public class WSMsgReq {
    Integer type;
    String data;
}
