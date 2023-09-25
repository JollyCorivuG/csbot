package com.jhc.csbot.model.vo.msg;

import com.jhc.csbot.model.vo.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 消息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgInfo {
    private Long id;
    private UserInfo senderInfo;
    private Integer type;
    private Object body;
    private Date sendTime;
}
