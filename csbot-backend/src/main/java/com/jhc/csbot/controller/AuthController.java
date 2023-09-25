package com.jhc.csbot.controller;

import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.model.vo.auth.AuthInfo;
import com.jhc.csbot.common.domain.vo.resp.BasicResponse;
import com.jhc.csbot.common.exception.ThrowUtils;
import com.jhc.csbot.service.IAuthService;
import com.jhc.csbot.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 认证控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@RestController
@RequestMapping("/capi/auth")
@Tag(name = "用户认证相关接口")
public class AuthController {
    @Resource
    private IAuthService authService;

    @GetMapping("/public/refresh/{rToken}")
    @Operation(summary = "刷新令牌")
    public BasicResponse<AuthInfo> refreshToken(@PathVariable("rToken") String rToken) {
        // 1.判断刷新令牌是否过期, 如果过期就必须重新登录
        ThrowUtils.throwIf(JwtUtils.isTokenExpired(rToken), ErrorStatus.FORBIDDEN_ERROR, "刷新令牌已过期");

        // 2.判断刷新令牌是否达到阈值, 如果达到阈值就重新生成刷新令牌
        Long userId = JwtUtils.getUIdFromToken(rToken);
        if (JwtUtils.isTokenReachThreshold(rToken, JwtUtils.R_TOKEN_THRESHOLD)) {
            return BasicResponse.success(authService.getTwoTokens(userId));
        }

        // 3.没达到阈值就只刷新aToken
        return BasicResponse.success(AuthInfo.builder()
                .accessToken(JwtUtils.generateAToken(userId))
                .refreshToken(rToken)
                .build());
    }
}
