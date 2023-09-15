package com.jhc.csbot.service;

import com.jhc.csbot.model.vo.auth.AuthInfo;

/**
 * @Description: 权限服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
public interface IAuthService {
    /**
     * 获取两个token
     * @param userId
     * @return
     */
    AuthInfo getTwoTokens(Long userId);
}
