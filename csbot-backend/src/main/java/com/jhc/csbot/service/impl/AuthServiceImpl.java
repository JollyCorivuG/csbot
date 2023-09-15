package com.jhc.csbot.service.impl;

import com.jhc.csbot.model.vo.auth.AuthInfo;
import com.jhc.csbot.service.IAuthService;
import com.jhc.csbot.utils.JwtUtils;
import org.springframework.stereotype.Service;

/**
 * @Description: 权限服务实现
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@Service
public class AuthServiceImpl implements IAuthService {
    @Override
    public AuthInfo getTwoTokens(Long userId) {
        return AuthInfo.builder()
                .aToken(JwtUtils.generateAToken(userId))
                .rToken(JwtUtils.generateRToken(userId))
                .build();
    }
}
