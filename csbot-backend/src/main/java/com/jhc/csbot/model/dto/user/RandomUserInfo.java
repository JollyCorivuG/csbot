package com.jhc.csbot.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 随机用户信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RandomUserInfo {
    private String nickName;
    private String avatar;
}
