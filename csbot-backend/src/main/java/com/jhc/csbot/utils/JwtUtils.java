package com.jhc.csbot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.common.exception.BizException;
import com.jhc.csbot.common.exception.ThrowUtils;

import java.util.Date;
import java.util.Map;

/**
 * @Description: JWT 工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
public class JwtUtils {
    // token 私钥
    private static final String TOKEN_SECRET = "JollyCorivuG";
    // access_token 过期时长 30 min
    private static final long A_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    // refresh_token 过期时长 7 days
    private static final long R_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;
    // refresh_token 阈值为 6 days
    public static final long R_TOKEN_THRESHOLD = 6 * 24 * 60 * 60 * 1000L;

    /**
     * 生成 token
     * @param userId
     * @param expireDate
     * @return
     */
    public static String generateToken(Long userId, Date expireDate) {
        // 1.创建 token 的 payload
        Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");

        // 2.生成 token
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userId)
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    /**
     * 生成 access_token
     * @param userId
     * @return
     */
    public static String generateAToken(Long userId) {
        // 1.得到 token 的过期时间
        Date expireDate = new Date(System.currentTimeMillis() + A_TOKEN_EXPIRE_TIME);

        // 2.生成 token
        return generateToken(userId, expireDate);
    }

    /**
     * 生成 refresh_token
     * @param userId
     * @return
     */
    public static String generateRToken(Long userId) {
        // 1.得到 token 的过期时间
        Date expireDate = new Date(System.currentTimeMillis() + R_TOKEN_EXPIRE_TIME);

        // 2.生成 token
        return generateToken(userId, expireDate);
    }

    /**
     * 验证 token 是否合法
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token) {
        // 1.验证 token
        DecodedJWT jwt;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            jwt = jwtVerifier.verify(token);
        } catch (TokenExpiredException e) {
            jwt = JWT.decode(token);
        } catch (Exception e) {
            throw new BizException(ErrorStatus.FORBIDDEN_ERROR, "token 不合法");
        }

        // 2.返回解码后的 jwt
        return jwt;
    }

    /**
     * 判断 token 是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        // 1.验证 token
        DecodedJWT jwt = verifyToken(token);

        // 2.判断 token 是否过期
        return jwt.getExpiresAt().before(new Date());
    }

    /**
     * 判断 token 是否快要过期
     * @param token
     * @param threshold
     * @return
     */
    public static boolean isTokenReachThreshold(String token, long threshold) {
        // 1.验证 token
        DecodedJWT jwt = verifyToken(token);

        // 2.判断 token 是否过期
        return jwt.getExpiresAt().before(new Date(System.currentTimeMillis() + threshold));
    }

    /**
     * 取出 token 中的 userId, 如果取不出 userId, 则抛出异常
     * @param token
     * @return
     */
    public static Long getUIdFromToken(String token) {
        // 1.验证 token
        DecodedJWT jwt = verifyToken(token);

        // 2.判断 token 中是否有 userId
        ThrowUtils.throwIf(jwt.getClaim("userId") == null, ErrorStatus.FORBIDDEN_ERROR, "token 不合法");

        // 3.返回 jwt 当中的 userId
        return jwt.getClaim("userId").asLong();
    }
}
