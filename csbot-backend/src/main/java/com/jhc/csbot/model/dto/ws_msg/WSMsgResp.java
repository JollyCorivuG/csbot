package com.jhc.csbot.model.dto.ws_msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 后端传给前端的 ws 消息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSMsgResp<T> {
    Integer type;
    T data;
}
