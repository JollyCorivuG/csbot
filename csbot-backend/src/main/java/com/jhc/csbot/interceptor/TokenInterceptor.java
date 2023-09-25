package com.jhc.csbot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.jhc.csbot.common.constants.SysConstants;
import com.jhc.csbot.common.domain.dto.RequestInfo;
import com.jhc.csbot.utils.JwtUtils;
import com.jhc.csbot.utils.RequestHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * Description: token 拦截器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@Order(1)
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_SCHEMA = "Bearer ";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 1.先判断是否是公开路径 (就是不需要登录就可以访问)
        if (isPublicURI(request.getRequestURI())) {
            return true;
        }

        // 2.判断是否携带 token
        String token = getToken(request);
        if (StrUtil.isBlank(token)) {
            response.setStatus(SysConstants.NO_AUTH_CODE);
            return false;
        }

        // 3.判断 token 是否过期
        if (JwtUtils.isTokenExpired(token)) {
            response.setStatus(SysConstants.NO_AUTH_CODE);
            return false;
        }

        // 4.将相关信息放入请求上下文
        RequestInfo info = new RequestInfo();
        info.setUid(JwtUtils.getUIdFromToken(token));
        info.setIp(request.getRemoteAddr());
        RequestHolder.set(info);
        return true;
    }

    /**
     * 判断是不是公共方法，可以未登录访问的
     * @param requestURI
     */
    private boolean isPublicURI(String requestURI) {
        String[] split = requestURI.split("/");
        return split.length > 2 && "public".equals(split[3]);
    }

    /**
     * 从请求头中获取 token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return Optional.ofNullable(header)
                .filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
                .map(h -> h.substring(AUTHORIZATION_SCHEMA.length()))
                .orElse(null);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        RequestHolder.remove();
    }
}
