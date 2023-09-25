package com.jhc.csbot.service.adapter;

import com.jhc.csbot.model.entity.User;
import com.jhc.csbot.model.vo.user.UserInfo;

/**
 * @Description: 用户适配器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
public class UserAdapter {
    public static UserInfo buildUserInfoResp(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickName(user.getNickName())
                .avatar(user.getAvatar())
                .build();
    }
}
