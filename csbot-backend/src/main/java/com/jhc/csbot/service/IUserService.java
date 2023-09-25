package com.jhc.csbot.service;

import com.jhc.csbot.model.dto.user.LoginForm;
import com.jhc.csbot.model.vo.auth.AuthInfo;
import com.jhc.csbot.model.vo.user.UserInfo;

/**
 * @Description: 用户服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
public interface IUserService {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    AuthInfo login(LoginForm loginForm);


    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(Long userId);
}
