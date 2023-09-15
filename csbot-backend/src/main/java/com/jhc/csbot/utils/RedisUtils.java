package com.jhc.csbot.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: Redis 工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
}
